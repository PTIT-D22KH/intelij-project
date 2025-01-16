package com.duongvct.service;

import com.duongvct.entity.OrderItem;

public interface OrderItemService {
    public void save(OrderItem orderItem);

    public void deleteById(Long id);
}
