package com.duongvct.repository;

import com.duongvct.entity.Account;
import com.duongvct.entity.Shipment;
import com.duongvct.utils.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    public Shipment findByOrderId(Long orderId);

    public List<Shipment> findByCustomerFullnameContaining(String searchValue);

    public List<Shipment> findByShipperFullnameContaining(String searchValue);

    public List<Shipment> findByShipmentStatus(ShipmentStatus shipmentStatus);

    public List<Shipment> findByCustomer(Account customer);


}
