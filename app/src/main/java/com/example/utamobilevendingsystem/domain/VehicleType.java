package com.example.utamobilevendingsystem.domain;

public enum VehicleType {

    CART(1, "Cart"),
    FOOD_TRUCK(2, "Food Truck");

    int id;
    String description;

    VehicleType(int id, String description){
        this.id = id;
        this.description = description;
    }
}
