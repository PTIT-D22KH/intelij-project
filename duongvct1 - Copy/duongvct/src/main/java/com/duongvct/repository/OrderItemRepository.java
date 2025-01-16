package com.duongvct.repository;

import com.duongvct.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
