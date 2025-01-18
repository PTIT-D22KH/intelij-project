package com.duongvct.controller;

import com.duongvct.entity.*;
import com.duongvct.service.impl.*;
import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private FoodItemServiceImpl foodItemService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @GetMapping("")
    public String showStore(Model model) {
        List<FoodItem> items = foodItemService.findAll();
        model.addAttribute("items", items);
        return "store";
    }

    @GetMapping("/cart")
    public String showCart(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        Account customer = accountService.findByUsername(user.getUsername());
        Order currentOrder = orderService.findCurrentOrderByCustomer(customer);
        model.addAttribute("order", currentOrder);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("itemId") Long itemId, @RequestParam("quantity") Long quantity, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        Account customer = accountService.findByUsername(user.getUsername());
        Order currentOrder = orderService.findCurrentOrderByCustomer(customer);

        if (currentOrder == null) {
            currentOrder = new Order();
            currentOrder.setCustomer(customer);
            currentOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
            currentOrder.setOrderStatus(OrderStatus.UNPAID);
            currentOrder.setOrderType(OrderType.ONLINE);
            currentOrder.setTotalAmount(0L);
            orderService.save(currentOrder);
        }

        FoodItem foodItem = foodItemService.findById(itemId);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(currentOrder);
        orderItem.setFoodItem(foodItem);
        orderItem.setQuantity(quantity);
        orderItemService.save(orderItem);

        currentOrder.getOrderItems().add(orderItem);
        currentOrder.setTotalAmount(currentOrder.getTotalAmount() + foodItem.getPrice() * quantity);
        orderService.save(currentOrder);

        return "redirect:/store/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("itemId") Long itemId, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        Account customer = accountService.findByUsername(user.getUsername());
        Order currentOrder = orderService.findCurrentOrderByCustomer(customer);

        if (currentOrder != null) {
            List<OrderItem> orderItems = currentOrder.getOrderItems();
            orderItems.removeIf(orderItem -> orderItem.getFoodItem().getId().equals(itemId));
            orderService.save(currentOrder);
        }

        return "redirect:/store/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        Account customer = accountService.findByUsername(user.getUsername());
        Order currentOrder = orderService.findCurrentOrderByCustomer(customer);

        if (currentOrder != null) {
            currentOrder.setOrderStatus(OrderStatus.UNPAID);
            orderService.save(currentOrder);
        }

        return "redirect:/store/order/manage";
    }

    @GetMapping("/order/manage")
    public String manageOrders(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        Account customer = accountService.findByUsername(user.getUsername());
        List<Order> orders = orderService.findByCustomer(customer);
        model.addAttribute("orders", orders);
        return "order/manage-orders";
    }

    @GetMapping("/order/{orderId}/food-items")
    public String chooseFoodItems(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("foodCategories", foodCategoryService.findAll());
        model.addAttribute("orderItems", order.getOrderItems());
        return "order/choose-food-items";
    }

    @GetMapping("/order/{orderId}/food-items/{categoryId}")
    @ResponseBody
    public List<FoodItem> getFoodItemsByCategory(@PathVariable Long orderId, @PathVariable Long categoryId) {
        return foodItemService.findByCategoryId(categoryId);
    }

    @GetMapping("/order/{id}/payment")
    public String paymentForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order/payment";
    }

    @PostMapping("/order/{id}/payment")
    public String processPayment(@ModelAttribute Order order, @PathVariable Long id) {
        Order existingOrder = orderService.findById(id);
        existingOrder.setDiscount(order.getDiscount());
        existingOrder.setPaidAmount(order.getPaidAmount());
        long amountAfterDiscount = existingOrder.getTotalAmount() * (100 - existingOrder.getDiscount()) / 100;

        if (existingOrder.getPaidAmount() >= amountAfterDiscount) {
            existingOrder.setOrderStatus(OrderStatus.PAID);
            existingOrder.setPayDate(new Timestamp(System.currentTimeMillis()));
            orderService.save(existingOrder);
        }
        return "redirect:/store/order/manage";
    }
}