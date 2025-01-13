package com.duongvct.utils;

public enum ShipmentStatus {
    TOPAY("topay", "Chờ xác nhận"),
    TOSHIP("toship", "Chờ lấy hàng"),
    TORECEIVE("toreceive", "Đang giao"),
    COMPLETED("completed", "Hoàn thành"),
    CANCELLED("cancelled", "Đã hủy"),;
    private String id, name;

    ShipmentStatus(String id, String name) {
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
