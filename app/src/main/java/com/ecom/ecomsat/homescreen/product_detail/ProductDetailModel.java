package com.ecom.ecomsat.homescreen.product_detail;

import io.realm.Realm;

public class ProductDetailModel implements ProductDetailMVP.IProductDetailModel {

    Realm realm = Realm.getDefaultInstance();

    @Override
    public void getProduct(String product_id) {
//        realm.where(CategoriesModel.class).findAll().where()
    }
}
