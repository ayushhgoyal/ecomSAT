package com.ecom.ecomsat.homescreen.models;

import io.realm.RealmObject;

public class TaxModel extends RealmObject {
    /**
     * name : VAT
     * value : 12.5
     */

    private String name;
    private double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
