package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") @Valid Product product, BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        if (newProductBindingResult.hasErrors()) {

            return "/admin/product/create";
        }

        String productImg = this.uploadService.handleSaveUploadFile(file, "product");

        product.setImage(productImg);
        // save data
        this.productService.createProduct(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.fetchProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Optional<Product> curentProduct = this.productService.fetchProductById(id);

        model.addAttribute("newProduct", curentProduct.get());

        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateproduct(Model model,
            @ModelAttribute("newProduct") @Valid Product pr, BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product curentProduct = this.productService.fetchProductById(pr.getId()).get();

        if (curentProduct != null) {

            // update new image
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                curentProduct.setImage(img);
            }
            curentProduct.setName(pr.getName());
            curentProduct.setPrice(pr.getPrice());
            curentProduct.setQuantity(pr.getQuantity());
            curentProduct.setDetailDesc(pr.getDetailDesc());
            curentProduct.setShortDesc(pr.getShortDesc());
            curentProduct.setFactory(pr.getFactory());
            curentProduct.setTarget(pr.getTarget());

            this.productService.createProduct(curentProduct);

        }
        return "redirect:/admin/product";
    }

}
