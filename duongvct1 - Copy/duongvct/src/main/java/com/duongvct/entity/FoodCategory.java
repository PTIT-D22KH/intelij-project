package com.duongvct.entity;

import com.duongvct.utils.StringToSlug;
import jakarta.persistence.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Entity
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String slug;

    @OneToMany(mappedBy = "category")
    private List<FoodItem> items;

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

    public String getSlug() {
        return StringToSlug.convert(name);
    }

    public void setSlug() {
        this.slug = StringToSlug.convert(name);
    }
}
