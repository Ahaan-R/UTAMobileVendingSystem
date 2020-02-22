package com.example.utamobilevendingsystem.domain;

import java.util.List;

public class Orders {

    private int orderId;
    private List<OrderItem> orderItems;
    private int quice;
    private Status status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getQuice() {
        return quice;
    }

    public void setQuice(int quice) {
        this.quice = quice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
