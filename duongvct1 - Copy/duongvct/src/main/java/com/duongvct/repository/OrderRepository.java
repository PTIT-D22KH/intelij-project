package com.duongvct.repository;

import com.duongvct.entity.Order;
import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByOrderType(OrderType orderType);

    public List<Order> findByCustomerFullnameContaining(String customerFullName);

//    public Order findById(Long id);

    public List<Order> findByOrderStatus(OrderStatus orderStatus);

}