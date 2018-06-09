package com.ecom.ecomsat.common.di;

import com.ecom.ecomsat.homescreen.api.ProductService;
import com.ecom.ecomsat.homescreen.models.ResponseModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ayushgoyal on 09/06/18.
 */

@Module
public class ApiModule {

    public static final String BASE_URL = "https://stark-spire-93433.herokuapp.com/";

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    Call<ResponseModel> provideProductList(Retrofit retrofit) {
        return retrofit.create(ProductService.class)
                .getProducts();
    }

}
