package com.ecom.ecomsat.homescreen.product_detail;

public class ProductDetailMVP {
    public interface IProductDetailView {

    }

    public interface IProductDetailPresenter {

        void getProductDetails(String product_id);
    }

    public interface IProductDetailModel {

        void getProduct(String product_id);
    }
}
