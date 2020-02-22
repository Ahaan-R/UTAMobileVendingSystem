package com.example.utamobilevendingsystem.domain;

public enum Status {

    IN_PROGRESS(1, "In Progress"),
    CONFIRMED(2, "Confirmed"),
    DECLINED(3, "Declined"),
    SUCCESS(4, "SUCCESS"),
    ORDER_COMPLETED(5, "Order Completed"),
    AVAILABLE(6, "Available"),
    UNAVAILABLE(7, "Unavailable");

    private int id;
    private String description;

    Status(int id, String description){
        this.id = id;
        this.description = description;
    }

}
