package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;

import java.util.ArrayList;
import java.util.HashSet;

import io.realm.RealmResults;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListMVP {
    public interface IProductListView {

        void refreshCategoryAdapter(ArrayList<CategoriesModel> categoriesModels);

        void refreshProductsAdapter(ArrayList<ProductsModel> productsModels, String name);

        void showGoToParentButton();

        void onProductListitemClick(int adapterPosition);

        void launchProductDetail(ProductsModel product);

        void onParentCategoryClicked(CategoriesModel categoriesModel);

        void refreshParentCategoryAdapter(ArrayList<CategoriesModel> parentCategoryModels);

        void showLoadingView();

        void hideLoadingView();

    }

    public interface IProductListPresenter {
        public void getProducts();

        void onCategoriesReceived();

        void onCategorySelected(CategoriesModel id);

        void onProductClicked(ProductsModel adapterPosition);

        ArrayList<String> getRankings();

        void getProductsForRank(String rank);

        void onParentCategorySelected(CategoriesModel categoriesModel);
    }

    public interface IProductListModel {
        public void fetchProductList();

        public void onCachedProductsReceived();

        public void onRemoteProductsReceived();

        ArrayList<String> getRankings();

        HashSet<ProductsModel> getProductsForRank(String rank);

        RealmResults<CategoriesModel> getSubCategories(Integer[] ids);

        ArrayList<ProductsModel> getAllProducts();
    }
}
