package com.duongvct.repository;

import com.duongvct.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    public Shipment findByOrderId(Long orderId);
}
