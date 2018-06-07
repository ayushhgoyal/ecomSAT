package com.ecom.ecomsat.homescreen.models;

import java.util.List;

public class ProductsModel {
    /**
     * id : 1
     * name : Nike Sneakers
     * date_added : 2016-12-09T11:16:11.000Z
     * variants : [{"id":1,"color":"Blue","size":42,"price":1000},{"id":2,"color":"Red","size":42,"price":1000},{"id":3,"color":"Blue","size":44,"price":1200},{"id":4,"color":"Red","size":44,"price":1200}]
     * tax : {"name":"VAT","value":12.5}
     */

    private int id;
    private String name;
    private String date_added;
    private TaxModel tax;
    private List<VariantsModel> variants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public TaxModel getTax() {
        return tax;
    }

    public void setTax(TaxModel tax) {
        this.tax = tax;
    }

    public List<VariantsModel> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantsModel> variants) {
        this.variants = variants;
    }
}