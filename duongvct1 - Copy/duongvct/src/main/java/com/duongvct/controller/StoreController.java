package com.duongvct.controller;

import com.duongvct.entity.*;
import com.duongvct.service.impl.*;
import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import com.duongvct.utils.ShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ShipmentServiceImpl shipmentService;

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

//    private Map<String, Map<Long, Integer>> userCarts = new HashMap<>();

    @GetMapping("")
    public String showStore(Model model) {
        List<FoodItem> items = foodItemService.findAll();
        model.addAttribute("items", items);
        return "store";
    }

    @GetMapping("/cart")
    public String showCart(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("items", foodItemService.findAll());
        return "cart";
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public String addToCart(@RequestParam("itemId") Long itemId, @RequestParam("quantity") int quantity, @AuthenticationPrincipal User user) {
        String username = user.getUsername();
//        userCarts.putIfAbsent(username, new HashMap<>());
//        Map<Long, Integer> cart = userCarts.get(username);
//        cart.put(itemId, cart.getOrDefault(itemId, 0) + quantity);
        return "Item added to cart";
    }

    @GetMapping("/order/create")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", accountService.findAllUsers());
        model.addAttribute("orderTypes", OrderType.values());
        return "order/create-order";
    }

    @PostMapping("/order/create")
    public String createOrder(@ModelAttribute Order order, @AuthenticationPrincipal User user) {
        Account customer = accountService.findByUsername(user.getUsername());
        order.setCustomer(customer);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.UNPAID);
        order.setTotalAmount(0L);
        orderService.save(order);
        return "redirect:/store/order/manage";
    }

    @GetMapping("/order/manage")
    public String manageOrders(@AuthenticationPrincipal User user, Model model) {
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
            if (existingOrder.getOrderType() == OrderType.ONLINE) {
                Shipment shipment = new Shipment();
                shipment.setOrder(existingOrder);
                shipment.setCustomer(existingOrder.getCustomer());
                shipment.setShipmentStatus(ShipmentStatus.TOSHIP);
                shipment.setStartDate(new Timestamp(System.currentTimeMillis()));
                shipmentService.save(shipment);
            }
        }
        orderService.save(existingOrder);
        return "redirect:/store/order/manage";
    }

    @GetMapping("/shipment/manage")
    public String manageShipments(@AuthenticationPrincipal User user, Model model) {
        Account customer = accountService.findByUsername(user.getUsername());
        List<Shipment> shipments = shipmentService.findByCustomer(customer);
        model.addAttribute("shipments", shipments);
        return "shipment/manage-shipments";
    }
}