package com.ecom.ecomsat.homescreen.models;

import io.realm.RealmObject;

public class VariantsModel extends RealmObject {
    /**
     * id : 1
     * color : Blue
     * size : 42
     * price : 1000
     */

    private int id;
    private String color;
    private int size;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
