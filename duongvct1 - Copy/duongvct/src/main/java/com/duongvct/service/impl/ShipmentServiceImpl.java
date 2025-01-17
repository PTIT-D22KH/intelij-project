package com.duongvct.service.impl;

import com.duongvct.entity.Account;
import com.duongvct.entity.Shipment;
import com.duongvct.repository.ShipmentRepository;
import com.duongvct.service.ShipmentService;
import com.duongvct.utils.ShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Override
    public List<Shipment> searchShipments(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return shipmentRepository.findAll();
        }
        switch (searchColumn) {
            case "id":
                Shipment shipment = shipmentRepository.findById(Long.parseLong(searchValue)).orElse(null);
                return shipment != null ? Collections.singletonList(shipment) : Collections.emptyList();
            case "order":
                Shipment orderShipment = shipmentRepository.findByOrderId(Long.parseLong(searchValue));
                return orderShipment != null ? Collections.singletonList(orderShipment) : Collections.emptyList();
            case "customer":
                return shipmentRepository.findByCustomerFullnameContaining(searchValue);
            case "shipper":
                return shipmentRepository.findByShipperFullnameContaining(searchValue);
            case "shipmentStatus":
                return shipmentRepository.findByShipmentStatus(ShipmentStatus.valueOf(searchValue));
            default:
                return shipmentRepository.findAll();
        }
    }

    @Override
    public List<Shipment> findByCustomer(Account customer) {
        return shipmentRepository.findByCustomer(customer);
    }


    @Override
    public Page<Shipment> findAll(Pageable pageable) {
        return shipmentRepository.findAll(pageable);
    }

    @Override
    public Page<Shipment> searchShipments(String searchColumn, String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isEmpty()) {
            return shipmentRepository.findAll(pageable);
        }
        switch (searchColumn) {
            case "id":
                Shipment shipment = shipmentRepository.findById(Long.parseLong(searchValue)).orElse(null);
                if (shipment != null) {
                    return new PageImpl<>(Collections.singletonList(shipment), pageable, 1);
                } else {
                    return new PageImpl<>(Collections.emptyList(), pageable, 0);
                }
            case "order":
                Shipment orderShipment = shipmentRepository.findByOrderId(Long.parseLong(searchValue));
                if (orderShipment != null) {
                    return new PageImpl<>(Collections.singletonList(orderShipment), pageable, 1);
                } else {
                    return new PageImpl<>(Collections.emptyList(), pageable, 0);
                }
            case "customer":
                return shipmentRepository.findByCustomerFullnameContaining(searchValue, pageable);
            case "shipper":
                return shipmentRepository.findByShipperFullnameContaining(searchValue, pageable);
            case "shipmentStatus":
                return shipmentRepository.findByShipmentStatus(ShipmentStatus.valueOf(searchValue), pageable);
            default:
                return shipmentRepository.findAll(pageable);
        }
    }
}