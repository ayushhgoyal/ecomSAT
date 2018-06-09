package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.common.MyApplication;

/**
 * Created by ayushgoyal on 08/06/18.
 */

class ProductListPresenter implements ProductListMVP.IProductListPresenter {

    ProductListModel productListModel;
    private ProductListFragmentView view;
    MyApplication myApplication;

    public ProductListPresenter(ProductListFragmentView view) {
        this.view = view;
        myApplication = (MyApplication) view.getActivity().getApplication();
        productListModel = new ProductListModel(myApplication);
    }

    @Override
    public void getProducts() {
        productListModel.fetchProductList();
    }
}
