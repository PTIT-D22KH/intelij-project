package com.duongvct.controller.admin;

import com.duongvct.entity.Shipment;
import com.duongvct.service.impl.AccountServiceImpl;
import com.duongvct.service.impl.OrderServiceImpl;
import com.duongvct.service.impl.ShipmentServiceImpl;
import com.duongvct.utils.OrderType;
import com.duongvct.utils.Role;
import com.duongvct.utils.ShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

import java.util.List;

@Controller
public class ShipmentManagerController {

    @Autowired
    private ShipmentServiceImpl shipmentService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/admin/shipment")
    public String showAllShipments(Model model) {
        List<Shipment> shipments = shipmentService.findAll();
        for (Shipment shipment : shipments) {
            System.out.println(shipment.getCustomer());
            System.out.println(shipment.getShipper());
        }
        model.addAttribute("shipments", shipments);
        return "admin/shipment/shipment-management";
    }


    @GetMapping("/admin/shipment/edit/{id}")
    public String editShipment(@PathVariable Long id, Model model) {
        Shipment shipment = shipmentService.findById(id);
        model.addAttribute("shipment", shipment);
        model.addAttribute("orders", orderService.findByOrderType(OrderType.ONLINE));
        model.addAttribute("shippers", accountService.findByRole(Role.ROLE_EMPLOYEE));
        model.addAttribute("shipmentStatuses", ShipmentStatus.values());
        return "admin/shipment/edit-shipment";
    }

    @PostMapping("/admin/shipment/edit/{id}")
    public String updateShipment(@ModelAttribute Shipment shipment, @PathVariable Long id) {
        Shipment existingShipment = shipmentService.findById(id);
        existingShipment.setShipmentStatus(shipment.getShipmentStatus());
        existingShipment.setEndDate(shipment.getEndDate());
        existingShipment.setOrder(shipment.getOrder());
        existingShipment.setCustomer(shipment.getCustomer());
        existingShipment.setShipper(shipment.getShipper());
        System.out.println(shipment.getCustomer());
        System.out.println(shipment.getShipper());
        existingShipment.setShipCost(shipment.getShipCost());
        if (existingShipment.getShipmentStatus() == ShipmentStatus.COMPLETED) {
            existingShipment.setEndDate(new Timestamp(System.currentTimeMillis()));
        }
        shipmentService.save(existingShipment);
        return "redirect:/admin/shipment";
    }

    @GetMapping("/admin/shipment/delete/{id}")
    public String deleteShipment(@PathVariable Long id) {
        shipmentService.deleteById(id);
        return "redirect:/admin/shipment";
    }

    @PostMapping("/admin/shipment/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            shipmentService.deleteById(id);
        }
        return "redirect:/admin/shipment";
    }

}
