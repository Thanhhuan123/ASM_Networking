package com.example.assignandroidnetworking.modal;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class Food  implements Serializable {
    private String name;
    private String image;
    private  String description;
    private  int price;
    private String category;

    public Food(String name, String image, String description, int price, String category) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
