package com.duongvct.service;

import com.duongvct.entity.Account;
import com.duongvct.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipmentService {
    List<Shipment> findAll();
    Shipment findById(Long id);
    Shipment save(Shipment shipment);
    void deleteById(Long id);

    public Shipment findByOrderId(Long orderId);
    public List<Shipment> searchShipments(String searchColumn, String searchValue);

    List<Shipment> findByCustomer(Account customer);

    Page<Shipment> findAll(Pageable pageable);

    Page<Shipment> searchShipments(String searchColumn, String searchValue, Pageable pageable);
}
