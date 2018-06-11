package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ayushgoyal on 08/06/18.
 */

class ProductListPresenter implements ProductListMVP.IProductListPresenter {

    ProductListModel productListModel;
    private ProductListFragmentView view;
    MyApplication myApplication;

    Realm realm = Realm.getDefaultInstance();
    Gson gson;

    public ArrayList<ProductsModel> getLoadedProductModels() {
        return loadedProductModels;
    }

    public void setLoadedProductModels(ArrayList<ProductsModel> loadedProductModels) {
        this.loadedProductModels = loadedProductModels;
    }

    ArrayList<ProductsModel> loadedProductModels = new ArrayList<>();

    public ProductListPresenter(ProductListFragmentView view) {
        this.view = view;
        myApplication = (MyApplication) view.getActivity().getApplication();
        productListModel = new ProductListModel(myApplication, this);
        gson = new Gson();
    }

    @Override
    public void getProducts() {
        productListModel.fetchProductList();
    }

    /**
     * Means data is fetched from api and saved in db,
     * now gather categories to display and pass to view
     * ONLY PARENT CATEGORIES
     */
    @Override
    public void onCategoriesReceived() {

        // get parent categories

        RealmResults<CategoriesModel> all = realm.where(CategoriesModel.class).findAll();

//        RealmQuery<CategoriesModel> query = realm.where(CategoriesModel.class).not();

        HashSet<Integer> child_categories = new HashSet<>();

        for (CategoriesModel categoriesModel :
                all) {
            child_categories.addAll(categoriesModel.getChild_categories());
        }


        Integer[] child_cat_array = child_categories.toArray(new Integer[child_categories.size()]);

        /**
         * find all top-parent categories
         * that do not lie in any of the child categories
         */
        all = realm.where(CategoriesModel.class).findAll().where().not()
                .in("id", child_cat_array).findAll();

        ArrayList<CategoriesModel> categoriesModels = new ArrayList<>
                (all);
        view.refreshCategoryAdapter(categoriesModels);

        // parent categories are displayed
        //------

//        view.hideGoToParentButton();

        // fetch products for all parent categories


//        Toast.makeText(view.getActivity(), "parent products to be fetched", Toast.LENGTH_SHORT).show();

        ArrayList<ProductsModel> productsForCategory = new ArrayList<>();

        for (CategoriesModel model :
                categoriesModels) {
            productsForCategory.addAll(productListModel.getProductsForCategory(model.getId()));
        }

        setLoadedProductModels(productsForCategory);
        view.refreshProductsAdapter(productsForCategory, "All");

    }

    /**
     * when cat selected, fetch subcategories and corresponding products
     *
     * @param
     */
    @Override
    public void onCategorySelected(CategoriesModel selectedCat) {

//        List<Integer> child_categories = realm.where(CategoriesModel.class).equalTo("id", id).findFirst()
//                .getChild_categories();

        int id = selectedCat.getId();

        ArrayList<ProductsModel> productsModels = productListModel.getProductsForCategory(id);

        ArrayList<CategoriesModel> categoriesModels = productListModel.getSubCategoriesForCategory(id);

//        ArrayList<ProductsModel> productsModels = new ArrayList<>(realm.
//                where(CategoriesModel.class).equalTo("id", id).findFirst().getProducts());
//        view.refreshProductsAdapter(productsModels);

        if (!categoriesModels.isEmpty()) {
            view.refreshCategoryAdapter(categoriesModels);
            view.showGoToParentButton();
//            view.setSelectedCategory(realm.where(CategoriesModel.class).equalTo("id", id).findFirst());
        }

        setLoadedProductModels(productsModels);
        view.refreshProductsAdapter(productsModels, selectedCat.getName());

    }

    @Override
    public void onProductClicked(int adapterPosition) {
        ProductsModel productsModel = getLoadedProductModels().get(adapterPosition);
        view.launchProductDetail(productsModel);
    }


    public void resetCategories() {
        onCategoriesReceived();
    }
}
