package com.duongvct.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.text.DecimalFormat;

@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String unitName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private FoodCategory category;


    @Column(nullable = false)
    private int price;

    @Transient
    private final DecimalFormat formatter = new DecimalFormat("###,###,###");

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DecimalFormat getFormatter() {
        return formatter;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitName='" + unitName + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", formatter=" + formatter +
                '}';
    }
}
