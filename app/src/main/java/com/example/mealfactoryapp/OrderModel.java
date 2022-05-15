package com.example.mealfactoryapp;

public class OrderModel {

    String itemCode, phone, quantity, unitPrice;

    OrderModel() {

    }

    public OrderModel(String itemCode, String phone, String quantity, String unitPrice) {
        this.itemCode = itemCode;
        this.phone = phone;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
