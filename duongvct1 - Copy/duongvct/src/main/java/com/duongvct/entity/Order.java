package com.duongvct.entity;

import com.duongvct.utils.OrderStatus;
import com.duongvct.utils.OrderType;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "`order`") // Escape the table name with backticks
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long paidAmount;

    @Column
    private Long totalAmount;

    @Column
    private Long discount;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderType orderType;

    @Column
    private Timestamp orderDate;

    @Column
    private Timestamp payDate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    private Table table;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Account customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        this.payDate = payDate;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }
}
