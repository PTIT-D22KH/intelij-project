package com.duongvct.service;

import com.duongvct.entity.Account;
import com.duongvct.entity.Order;
import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    void save(Order order);
    void deleteById(Long id);

    public List<Order> findByOrderType(OrderType orderType);

    public List<Order> searchOrders(String searchColumn, String searchValue);

    List<Order> findByCustomer(Account customer);


    Page<Order> findAll(Pageable pageable);

    Page<Order> searchOrders(String searchColumn, String searchValue, Pageable pageable);

    Order findCurrentOrderByCustomer(Account customer);
}