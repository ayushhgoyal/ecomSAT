package com.ecom.ecomsat.homescreen.product_detail;

import com.ecom.ecomsat.homescreen.models.ProductsModel;

public class ProductDetailPresenter implements ProductDetailMVP.IProductDetailPresenter {


    private final ProductDetailModel productDetailModel;
    private ProductDetailFragmentView productDetailFragmentView;
    private ProductsModel product;

    public ProductDetailPresenter(ProductDetailFragmentView productDetailFragmentView, ProductsModel product) {
        this.productDetailFragmentView = productDetailFragmentView;
        this.product = product;
        productDetailModel = new ProductDetailModel();
    }

    @Override
    public void getProductDetails(String product_id) {
        productDetailModel.getProduct(product_id);
    }
}
