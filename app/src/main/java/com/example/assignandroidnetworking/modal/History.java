package com.example.assignandroidnetworking.modal;

public class History {
    Cart cart;
    private String id;
    private String fullName;
    private String phone;
    private String address;
    private String time;
    private  String paymentMethod;

    public History(Cart cart, String id, String fullName, String phone, String address, String time, String paymentMethod) {
        this.cart = cart;
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.time = time;
        this.paymentMethod = paymentMethod;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
