package com.duongvct.service.impl;

import com.duongvct.entity.Account;
import com.duongvct.entity.Order;
import com.duongvct.repository.OrderRepository;
import com.duongvct.service.OrderService;
import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findByOrderType(OrderType orderType) {
        return orderRepository.findByOrderType(orderType);
    }

    @Override
    public List<Order> searchOrders(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return orderRepository.findAll();
        }
        switch (searchColumn) {
            case "id":
                Order order = orderRepository.findById(Long.parseLong(searchValue)).orElse(null);
                if (order != null) {
                    return List.of(order);
                } else {
                    return List.of();
                }
            case "customer":
                return orderRepository.findByCustomerFullnameContaining(searchValue);
            case "orderStatus":
                return orderRepository.findByOrderStatus(OrderStatus.valueOf(searchValue));
            case "orderType":
                return orderRepository.findByOrderType(OrderType.valueOf(searchValue));
            default:
                return orderRepository.findAll();
        }
    }

    @Override
    public List<Order> findByCustomer(Account customer) {
        return orderRepository.findByCustomer(customer);
    }


    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> searchOrders(String searchColumn, String searchValue, Pageable pageable) {
        // Implement search logic with pagination here
        if (searchValue == null || searchValue.isEmpty()) {
            return orderRepository.findAll(pageable);
        }
        switch (searchColumn) {
            case "id":
                Order order = orderRepository.findById(Long.parseLong(searchValue)).orElse(null);
                if (order != null) {
                    return new PageImpl<>(List.of(order), pageable, 1);
                } else {
                    return new PageImpl<>(List.of(), pageable, 0);
                }
            case "customer":
                return orderRepository.findByCustomerFullnameContaining(searchValue, pageable);
            case "orderStatus":
                return  orderRepository.findByOrderStatus(OrderStatus.valueOf(searchValue), pageable);
            case "orderType":
                return orderRepository.findByOrderType(OrderType.valueOf(searchValue), pageable);
            default:
                return orderRepository.findAll(pageable);
        }
    }

    @Override
    public Order findCurrentOrderByCustomer(Account customer) {
        return orderRepository.findByCustomerAndOrderStatus(customer, OrderStatus.UNPAID);
    }
}