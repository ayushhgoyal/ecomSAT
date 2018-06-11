package com.ecom.ecomsat.homescreen.product_detail;

import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.models.VariantsModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProductDetailModel implements ProductDetailMVP.IProductDetailModel {

    Realm realm = Realm.getDefaultInstance();

    @Override
    public void getProduct(String product_id) {
//        realm.where(CategoriesModel.class).findAll().where()
    }

    @Override
    public ArrayList<String> getColorsForSize(ProductsModel product, int size) {

        RealmResults<VariantsModel> variantsModels =
                product.getVariants().where().
                        equalTo("size", size).distinct("color").findAll();

        ArrayList<String> colors = new ArrayList<>();

        for (VariantsModel variantsModel :
                variantsModels) {
            colors.add(variantsModel.getColor());
        }

        return colors;
    }

    @Override
    public VariantsModel getProductVariant(ProductsModel product, Integer selectedSize, String color) {
        VariantsModel variantsModels = product.getVariants().where()
                .equalTo("size", selectedSize)
                .and()
                .equalTo("color", color).findFirst();
        return variantsModels;
    }

    @Override
    public VariantsModel getProductVariant(ProductsModel product, String color) {
        VariantsModel variantsModels = product.getVariants().where()
                .equalTo("color", color).findFirst();
        return variantsModels;
    }

    @Override
    public ArrayList<String> getAllColors(ProductsModel product) {
        RealmList<VariantsModel> variants = product.getVariants();

        ArrayList<String> colors = new ArrayList<>();

        for (VariantsModel variantsModel :
                variants) {
            colors.add(variantsModel.getColor());
        }

        return colors;

    }
}
