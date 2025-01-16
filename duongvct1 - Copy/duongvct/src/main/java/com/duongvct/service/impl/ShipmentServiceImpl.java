package com.duongvct.service.impl;

import com.duongvct.entity.Shipment;
import com.duongvct.repository.ShipmentRepository;
import com.duongvct.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment findById(Long id) {
        return shipmentRepository.findById(id).orElse(null);
    }

    @Override
    public Shipment save(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Override
    public void deleteById(Long id) {
        shipmentRepository.deleteById(id);
    }

    @Override
    public Shipment findByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }
}