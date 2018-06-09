package com.ecom.ecomsat.homescreen.product_list;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListMVP {
    public interface IProductListView {

    }

    public interface IProductListPresenter {
        public void getProducts();
    }

    public interface IProductListModel {
        public void fetchProductList();

        public void onCachedProductsReceived();

        public void onRemoteProductsReceived();
    }
}
