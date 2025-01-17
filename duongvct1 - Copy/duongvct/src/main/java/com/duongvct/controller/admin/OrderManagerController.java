package com.duongvct.controller.admin;

import com.duongvct.entity.*;
import com.duongvct.service.FoodItemService;
import com.duongvct.service.TableService;
import com.duongvct.service.impl.*;
import com.duongvct.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderManagerController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private TableServiceImpl tableService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private FoodItemServiceImpl foodItemService;

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;
    @Autowired
    private OrderItemServiceImpl  orderItemService;

    @Autowired
    private ShipmentServiceImpl shipmentService;

//    @GetMapping("/admin/order")
//    public String showOrders(Model model) {
//        List<Order> orders = orderService.findAll();
//        model.addAttribute("orders", orders);
//        return "admin/order/order-management";
//    }

//    @GetMapping("/admin/order")
//    public String showOrders(@RequestParam(value = "searchColumn", required = false) String searchColumn,
//                             @RequestParam(value = "searchValue", required = false) String searchValue,
//                             Model model) {
//        List<Order> orders;
//        if (searchColumn != null && searchValue != null) {
//            orders = orderService.searchOrders(searchColumn, searchValue);
//        } else {
//            orders = orderService.findAll();
//        }
//        model.addAttribute("orders", orders);
//        return "admin/order/order-management";
//    }

    @GetMapping("/admin/order")
    public String showOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                             @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                             @RequestParam(value = "searchColumn", required = false) String searchColumn,
                             @RequestParam(value = "searchValue", required = false) String searchValue,
                             Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortField));
        Page<Order> orders;
        if (searchColumn != null && searchValue != null) {
            orders = orderService.searchOrders(searchColumn, searchValue, pageable);
        } else {
            orders = orderService.findAll(pageable);
        }
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("totalItems", orders.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "admin/order/order-management";
    }

    @GetMapping("/admin/order/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", accountService.findAllUsers());
        model.addAttribute("tables", tableService.findAllFreeTables());
        model.addAttribute("orderTypes", OrderType.values());
        return "admin/order/add-order";
    }

    @PostMapping("/admin/order/add")
    public String addOrder(@ModelAttribute Order order, Model model) {
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.UNPAID);
        //set table status if order type is LOCAL
        Table table = tableService.findById(order.getTable().getId());
        if (order.getOrderType() == OrderType.LOCAL) {
            table.setTableStatus(TableStatus.SERVING);
        }
        tableService.save(table);
        //
        order.setTotalAmount(0L);
        orderService.save(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("customers", accountService.findAllUsers());
        model.addAttribute("tables", tableService.findAll());
        model.addAttribute("orderTypes", OrderType.values());
        return "admin/order/edit-order";
    }

    @PostMapping("/admin/order/edit/{id}")
    public String updateOrder(@ModelAttribute Order order, @PathVariable Long id) {
//        System.out.println("Order: " + order);
        Order existingOrder = orderService.findById(id);
//        System.out.println("Existing order: " + existingOrder);
        existingOrder.setCustomer(order.getCustomer());
        if (shipmentService.findByOrderId(existingOrder.getId()) != null) {
            Shipment shipment = shipmentService.findByOrderId(existingOrder.getId());
            shipment.setCustomer(order.getCustomer());
            shipmentService.save(shipment);
        }
//        existingOrder.setOrderDate(order.getOrderDate());
//        existingOrder.setOrderStatus(order.getOrderStatus());
//        existingOrder.setOrderType(order.getOrderType());
        if (order.getTable() != null) {
            existingOrder.setTable(order.getTable());
        }

//        existingOrder.setTotalAmount(order.getTotalAmount());
        List<OrderItem> orderItems = order.getOrderItems();

//        for (OrderItem orderItem : orderItems) {
//            orderItem.setOrder(existingOrder);
//            orderItemService.save(orderItem);
//        }

//        existingOrder.setOrderItems(orderItems);
        existingOrder.getOrderItems().clear();
        long totalAmount = existingOrder.getTotalAmount();
        if (orderItems != null) {
            for (OrderItem item : orderItems) {
//            System.out.println("Order Item: " + item);
                item.setOrder(existingOrder);
                orderItemService.save(item);
                existingOrder.getOrderItems().add(item);
                FoodItem foodItem = foodItemService.findById(item.getFoodItem().getId());
                FoodItem toppingItem = foodItemService.findById(item.getToppingItem().getId());
                totalAmount += item.getQuantity() * (foodItem.getPrice() + toppingItem.getPrice());
            }
        }

//        System.out.println("Total amount: " + totalAmount);
        existingOrder.setTotalAmount(totalAmount);
//        System.out.println("Existing order after set: " + existingOrder);
        orderService.save(existingOrder);
        return "redirect:/admin/order";
    }

    @PostMapping("/admin/order/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            List<OrderItem> orderItems = orderService.findById(id).getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItemService.deleteById(orderItem.getId());
            }
            orderService.deleteById(id);
        }
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        List<OrderItem> orderItems = orderService.findById(id).getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItemService.deleteById(orderItem.getId());
        }
        orderService.deleteById(id);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/{orderId}/food-items/")
    public String chooseFoodItems(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("foodCategories", foodCategoryService.findAll());
        model.addAttribute("orderItems", order.getOrderItems());
        return "admin/order/choose-food-items";
    }

    @GetMapping("/admin/order/{orderId}/food-items/{categoryId}")
    @ResponseBody
    public List<FoodItem> getFoodItemsByCategory(@PathVariable Long orderId, @PathVariable Long categoryId) {
        return foodItemService.findByCategoryId(categoryId);
    }

    @GetMapping("/admin/order/{id}/payment")
    public String paymentForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "admin/order/payment";
    }

    @PostMapping("/admin/order/{id}/payment")
    public String processPayment(@ModelAttribute Order order, @PathVariable Long id) {
        Order existingOrder = orderService.findById(id);
        existingOrder.setDiscount(order.getDiscount());
        existingOrder.setPaidAmount(order.getPaidAmount());
        // Calculate the amount after applying the discount percentage
        long amountAfterDiscount = existingOrder.getTotalAmount() * (100 - existingOrder.getDiscount()) / 100;

        // Check if the paid amount is greater than or equal to the amount after discount
        if (existingOrder.getPaidAmount() >= amountAfterDiscount) {
            existingOrder.setOrderStatus(OrderStatus.PAID);
            existingOrder.setPayDate(new Timestamp(System.currentTimeMillis()));
            if (existingOrder.getOrderType() == OrderType.ONLINE) {
                Shipment shipment = new Shipment();
                shipment.setOrder(existingOrder);
                shipment.setCustomer(existingOrder.getCustomer());
                shipment.setShipmentStatus(ShipmentStatus.TOSHIP);
                shipment.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
                shipmentService.save(shipment);

            }
            Table table = existingOrder.getTable();
            table.setTableStatus(TableStatus.FREE);
            tableService.save(table);
        }
        orderService.save(existingOrder);
        return "redirect:/admin/order";
    }


}