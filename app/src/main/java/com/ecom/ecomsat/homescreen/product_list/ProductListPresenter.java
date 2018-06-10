package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;

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

    public ProductListPresenter(ProductListFragmentView view) {
        this.view = view;
        myApplication = (MyApplication) view.getActivity().getApplication();
        productListModel = new ProductListModel(myApplication, this);
    }

    @Override
    public void getProducts() {
        productListModel.fetchProductList();
    }

    /**
     * Means data is fetched from api and saved in db,
     * now gather categories to display and pass to view
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

        all = realm.where(CategoriesModel.class).findAll().where().not()
                .in("id", child_cat_array).findAll();


        ArrayList<CategoriesModel> categoriesModels = new ArrayList<>
                (all);
        view.refreshCategoryAdapter(categoriesModels);

        // parent categories are displayed
        //------

        view.hideGoToParentButton();

        // fetch products for first parent category


//        Toast.makeText(view.getActivity(), "parent products to be fetched", Toast.LENGTH_SHORT).show();

        ArrayList<ProductsModel> productsForCategory = productListModel.getProductsForCategory(categoriesModels.get(0).getId());
        view.refreshProductsAdapter(productsForCategory);

    }

    /**
     * when cat selected, fetch subcategories and corresponding products
     *
     * @param id
     */
    @Override
    public void onCategorySelected(int id) {

//        List<Integer> child_categories = realm.where(CategoriesModel.class).equalTo("id", id).findFirst()
//                .getChild_categories();

        ArrayList<ProductsModel> productsModels = productListModel.getProductsForCategory(id);

        ArrayList<CategoriesModel> categoriesModels = productListModel.getSubCategoriesForCategory(id);

//        ArrayList<ProductsModel> productsModels = new ArrayList<>(realm.
//                where(CategoriesModel.class).equalTo("id", id).findFirst().getProducts());
//        view.refreshProductsAdapter(productsModels);

        if (!categoriesModels.isEmpty()) {
            view.refreshCategoryAdapter(categoriesModels);
            view.showGoToParentButton();
            view.setParentCategory(realm.where(CategoriesModel.class).equalTo("id", id).findFirst());
        }

        view.refreshProductsAdapter(productsModels);

    }


}
