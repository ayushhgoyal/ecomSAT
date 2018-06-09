package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.ResponseModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListModel implements ProductListMVP.IProductListModel {


    private MyApplication myApplication;

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
                responseModel.getCategories();
                
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
