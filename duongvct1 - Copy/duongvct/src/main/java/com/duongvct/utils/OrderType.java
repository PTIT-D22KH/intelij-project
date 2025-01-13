package com.duongvct.utils;

public enum OrderType {
    LOCAL("local", "Tại quán"),
    ONLINE("online", "Đặt hàng");
    private String id, name;

    OrderType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
