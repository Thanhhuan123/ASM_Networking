package com.example.assignandroidnetworking.modal;

public class Cart {
    private  Food food;
    private  int quantity;
    private  String id;

    public Cart(Food food, int quantity, String id) {
        this.food = food;
        this.quantity = quantity;
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
