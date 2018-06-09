package com.ecom.ecomsat.common.di;


import com.ecom.ecomsat.homescreen.product_list.ProductListModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ayushgoyal on 09/06/18.
 */

@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(ProductListModel productListModel);
}
