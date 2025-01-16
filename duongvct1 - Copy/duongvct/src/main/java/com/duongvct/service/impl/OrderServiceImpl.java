package com.duongvct.service.impl;

import com.duongvct.entity.Order;
import com.duongvct.repository.OrderRepository;
import com.duongvct.service.OrderService;
import com.duongvct.utils.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
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
}