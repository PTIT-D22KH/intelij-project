package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.*;
import vn.hoidanit.laptopshop.repository.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);

    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();

    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // Check user đã có cart chưa ? nếu chưa => tạo mới
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // Tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail
            // tìm product by id

            Optional<Product> producOptional = this.productRepository.findById(productId);
            if (producOptional.isPresent()) {
                Product realProduct = producOptional.get(); // Lấy ra đối tượng
                // Check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail oldDetail=  this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                //
                if(oldDetail== null){
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(1);
                    this.cartDetailRepository.save(cd);
                    // update cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else{
                    oldDetail.setQuantity(oldDetail.getQuantity()+1);
                    this.cartDetailRepository.save(oldDetail);

                }
            }

        }
    }
    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }
    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if(cartDetailOptional.isPresent()){
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);
            // update cart
            if(currentCart.getSum() > 1){
                // update current cart
                int s = currentCart.getSum()-1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            }else{
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
                // this.cartRepository.save(currentCart);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for(CartDetail cartDetail : cartDetails){
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if(cdOptional.isPresent()){
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }
    public void handlePlaceOrder(
            User user,  HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone){
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(receiverName);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverPhone(receiverPhone);
        // hứng order
        order =  this.orderRepository.save(order);
        // create orderDetail
        // Step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if(cart != null){
            List<CartDetail> cartDetails = cart.getCartDetails();
            if(cartDetails != null){
                for(CartDetail cd:cartDetails){
                    OrderDetail  orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }
                // Step 2: delete cart_detail and cart
                // Xóa thằng con cart_detail
                for(CartDetail cd : cartDetails){
                    this.cartDetailRepository.deleteById(cd.getId());
                }
                // delete cart
                this.cartRepository.deleteById(cart.getId());
                // step 3: update session reset lại giỏ hàng
                session.setAttribute("sum", 0);
            }
        }
    }
}
