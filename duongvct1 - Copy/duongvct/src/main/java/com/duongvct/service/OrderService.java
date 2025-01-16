package com.duongvct.service;

import com.duongvct.entity.Order;
import com.duongvct.utils.OrderType;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    void save(Order order);
    void deleteById(Long id);

    public List<Order> findByOrderType(OrderType orderType);
}