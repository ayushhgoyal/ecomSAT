package com.ecom.ecomsat.homescreen.product_detail;

import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.models.VariantsModel;

import java.util.ArrayList;

public class ProductDetailMVP {
    public interface IProductDetailView {

        void onSizeClicked(int position);

        void setColorsAdapter(ArrayList<String> colorList);

        void onColorClicked(int adapterPosition);

        void setAmounts(int price, double tax, double total_amount);
    }

    public interface IProductDetailPresenter {

        void getProductDetails(String product_id);

        ArrayList<Integer> getSizeList();

        void onSizeSelected(int position);

        void onColorSelected(int adapterPosition);
    }

    public interface IProductDetailModel {

        void getProduct(String product_id);

        ArrayList<String> getColorsForSize(ProductsModel product, int size);

        VariantsModel getProductVariant(ProductsModel product, Integer selectedSize, String color);
    }
}
