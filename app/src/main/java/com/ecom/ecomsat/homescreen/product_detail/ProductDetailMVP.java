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


        ArrayList<Integer> getSizeList();

        void onSizeSelected(int position);

        void onColorSelected(int adapterPosition);

        /**
         * Fetches colors without size
         */
        void onSizeNotApplicable();
    }

    public interface IProductDetailModel {

        void getProduct(String product_id);

        ArrayList<String> getColorsForSize(ProductsModel product, int size);

        ArrayList<String> getAllColors(ProductsModel product);

        /**
         * considers size also
         *
         * @param product
         * @param selectedSize
         * @param color
         * @return
         */
        VariantsModel getProductVariant(ProductsModel product, Integer selectedSize, String color);

        /**
         * doesnt consider size
         *
         * @param product
         * @param color
         * @return
         */
        VariantsModel getProductVariant(ProductsModel product, String color);
    }
}
