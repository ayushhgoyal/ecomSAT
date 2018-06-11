package com.ecom.ecomsat.homescreen.product_list;

import android.util.Log;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.models.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListModel implements ProductListMVP.IProductListModel {


    private MyApplication myApplication;
    private ProductListPresenter productListPresenter;

    Realm realm = Realm.getDefaultInstance();

    @Inject
    Call<ResponseModel> productListCall;

    public ProductListModel(MyApplication myApplication, ProductListPresenter productListPresenter) {
        this.myApplication = myApplication;
        this.productListPresenter = productListPresenter;
        myApplication.getDaggerApiComponent().inject(this);
    }

    @Override
    public void fetchProductList() {
        productListCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();
//                responseModel.getCategories();
//                realm.beginTransaction();
//                realm.copyToRealm(responseModel.getCategories());
//                realm.copyToRealm(responseModel.getRankings());
//                realm.commitTransaction();


                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(responseModel.getCategories());
                        realm.copyToRealmOrUpdate(responseModel.getRankings());
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d("ProductListModel",
                                "realm.where(CategoriesModel.class).findAll():"
                                        + realm.where(CategoriesModel.class).findAll().size());

                        productListPresenter.onCategoriesReceived();
                    }
                });


            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCachedProductsReceived() {

    }

    @Override
    public void onRemoteProductsReceived() {

    }

    /**
     * Fetches all products for this category and its all child categories
     *
     * @param id
     * @return
     */
    public ArrayList<ProductsModel> getProductsForCategory(int id) {

        ArrayList<ProductsModel> productsModels = new ArrayList<>();

        CategoriesModel categoryModel = realm.where(CategoriesModel.class)
                .equalTo("id", id).findFirst();

        // get all products for this passed category
        productsModels.addAll(categoryModel.getProducts());

        // now get all underlying child categories for this category and
        // then fetch all products for all these categories
        // and keep saving them in same arraylist - finally send it across to shine


        if (!categoryModel.getChild_categories().isEmpty()) { // if child categories are present
            for (CategoriesModel model :
                    getSubCategoriesForCategory(categoryModel.getId())) {
                productsModels.addAll(getProductsForCategory(model.getId()));
            }
        }


        return productsModels;
    }

    /**
     * Fetches child categories of passed category - only one level deep
     *
     * @param id
     * @return
     */
    public ArrayList<CategoriesModel> getSubCategoriesForCategory(int id) {
        List<Integer> child_cat_ids = realm.where(CategoriesModel.class)
                .equalTo("id", id).findFirst().getChild_categories();

        Integer[] ids = child_cat_ids.toArray(new Integer[child_cat_ids.size()]);

        ArrayList<CategoriesModel> child_cat_models = new ArrayList<CategoriesModel>
                (realm.where(CategoriesModel.class).in("id", ids).findAll());

        return child_cat_models;
    }
}
