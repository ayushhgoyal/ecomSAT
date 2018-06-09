package com.ecom.ecomsat.homescreen.models;

import io.realm.RealmObject;

public class RankedProductModel extends RealmObject {
    /**
     * id : 1
     * view_count : 56700
     */

    private int id;
    private int view_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }
}
