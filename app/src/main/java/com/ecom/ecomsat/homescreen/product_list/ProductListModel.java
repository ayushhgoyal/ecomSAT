package com.ecom.ecomsat.homescreen.product_list;

import android.util.Log;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ResponseModel;

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
    Realm realm = Realm.getDefaultInstance();


    @Inject
    Call<ResponseModel> productListCall;

    public ProductListModel(MyApplication myApplication) {
        this.myApplication = myApplication;
        myApplication.getDaggerApiComponent().inject(this);
    }

    @Override
    public void fetchProductList() {
        productListCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();
//                responseModel.getCategories();

                realm.copyToRealm(responseModel.getCategories());
                realm.copyToRealm(responseModel.getRankings());

                Log.d("ProductListModel",
                        "realm.where(CategoriesModel.class).findAll():"
                                + realm.where(CategoriesModel.class).findAll().size());
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
}
