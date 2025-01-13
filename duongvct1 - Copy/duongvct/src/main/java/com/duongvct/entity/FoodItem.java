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

    private final DecimalFormat formatter = new DecimalFormat("###,###,###");


}
