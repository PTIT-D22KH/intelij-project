package com.duongvct.entity;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long quantity;


    @OneToOne()
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @OneToOne
    @JoinColumn(name = "topping_id")
    private FoodItem toppingItem;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public FoodItem getToppingItem() {
        return toppingItem;
    }

    public void setToppingItem(FoodItem toppingItem) {
        this.toppingItem = toppingItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
