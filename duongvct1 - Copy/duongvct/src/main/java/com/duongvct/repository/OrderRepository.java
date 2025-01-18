package com.duongvct.repository;

import com.duongvct.entity.Account;
import com.duongvct.entity.Order;
import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> , PagingAndSortingRepository<Order, Long> {
    List<Order> findByOrderType(OrderType orderType);

    List<Order> findByCustomerFullnameContaining(String customerFullName);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByCustomer(Account customer);

    Page<Order> findByCustomerFullnameContaining(String customerFullName, Pageable pageable);

    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    Page<Order> findByOrderType(OrderType orderType, Pageable pageable);


    Order findByCustomerAndOrderStatus(Account customer, OrderStatus orderStatus);
}