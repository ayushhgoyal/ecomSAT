package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;

import java.util.ArrayList;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListMVP {
    public interface IProductListView {

        void refreshCategoryAdapter(ArrayList<CategoriesModel> categoriesModels);

        void refreshProductsAdapter(ArrayList<ProductsModel> productsModels);

        void hideGoToParentButton();

        void showGoToParentButton();

        void setParentCategory(CategoriesModel parentCategory);
    }

    public interface IProductListPresenter {
        public void getProducts();

        void onCategoriesReceived();

        void onCategorySelected(int id);
    }

    public interface IProductListModel {
        public void fetchProductList();

        public void onCachedProductsReceived();

        public void onRemoteProductsReceived();
    }
}
