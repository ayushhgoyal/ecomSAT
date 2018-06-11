package com.ecom.ecomsat.homescreen.product_detail;

import com.ecom.ecomsat.homescreen.models.ProductsModel;

import java.util.ArrayList;

public class ProductDetailMVP {
    public interface IProductDetailView {

        void onSizeClicked(int position);

        void setColorsAdapter(ArrayList<String> colorList);
    }

    public interface IProductDetailPresenter {

        void getProductDetails(String product_id);

        ArrayList<Integer> getSizeList();

        ArrayList<String> getColorList();

        void onSizeSelected(int position);
    }

    public interface IProductDetailModel {

        void getProduct(String product_id);

        ArrayList<String> getColorsForSize(ProductsModel product, int size);
    }
}
