package com.ecom.ecomsat.homescreen.product_list;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmList;
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

    public ArrayList<CategoriesModel> getParentCategoryModels() {
        return parentCategoryModels;
    }

    private ArrayList<CategoriesModel> parentCategoryModels;

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
        view.showLoadingView();

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

        parentCategoryModels = new ArrayList<>();
        CategoriesModel allCategoryModel = new CategoriesModel();
        allCategoryModel.setId(-1);
        allCategoryModel.setName("All");

        RealmList<Integer> child = new RealmList<>();

        for (CategoriesModel model :
                all) {
            child.add(model.getId());
        }

        allCategoryModel.setChild_categories(child);
        parentCategoryModels.add(allCategoryModel);

        view.refreshParentCategoryAdapter(parentCategoryModels);

//        view.rvParentCategoryList.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        onParentCategorySelected(parentCategoryModels.get(0));

        // parent categories are displayed
        //------


        // fetch products for all parent categories

        getAllProductsAndDisplay();

        view.hideLoadingView();

    }

    private void getAllProductsAndDisplay() {
        ArrayList<ProductsModel> productsForCategory = productListModel.getAllProducts();

        setLoadedProductModels(productsForCategory);
        view.refreshProductsAdapter(productsForCategory, "All");
    }

    /**
     * when cat selected, fetch subcategories and corresponding products
     * and add to parent category
     *
     * @param
     */
    @Override
    public void onCategorySelected(CategoriesModel selectedCat) {


        int id = selectedCat.getId();

        ArrayList<ProductsModel> productsModels = productListModel.getProductsForCategory(id);

        ArrayList<CategoriesModel> subCategoriesModels = productListModel.getSubCategoriesForCategory(id);


        if (!subCategoriesModels.isEmpty()) {
            if (!getParentCategoryModels().contains(selectedCat)) {
                getParentCategoryModels().add(selectedCat);
                view.refreshParentCategoryAdapter(getParentCategoryModels());
            }

            view.refreshCategoryAdapter(subCategoriesModels);
            view.showGoToParentButton();
        }

        setLoadedProductModels(productsModels);
        view.refreshProductsAdapter(productsModels, selectedCat.getName());

    }

    @Override
    public void onProductClicked(int adapterPosition) {
        ProductsModel productsModel = getLoadedProductModels().get(adapterPosition);
        view.launchProductDetail(productsModel);
    }

//    @Override
//    public void loadMostViewed() {
//        productListModel.getMostViewedProducts();
//    }

    @Override
    public ArrayList<String> getRankings() {
        ArrayList<String> rankings = productListModel.getRankings();
        return rankings;
    }

    @Override
    public void getProductsForRank(String rank) {
        HashSet<ProductsModel> rankedProducts = productListModel.getProductsForRank(rank);

        /**
         *  now we will intersect fetched products for rank
         *  and already loaded products
         *  so that sorting will happen only in current selection of category
         */

        HashSet<ProductsModel> loadedProducts = new HashSet<>(getLoadedProductModels());

//        loadedProducts.(rankedProducts);
        rankedProducts.retainAll(loadedProducts);

        view.refreshProductsAdapter(new ArrayList<>(rankedProducts), rank);

    }

    @Override
    public void onParentCategorySelected(CategoriesModel categoriesModel) {

        // remove categories till here

        int index = getParentCategoryModels().indexOf(categoriesModel);
        for (int i = getParentCategoryModels().size() - 1; i > index; i--) {
            getParentCategoryModels().remove(i);
        }
        view.refreshParentCategoryAdapter(getParentCategoryModels());

//        productListModel.getSubCategoriesForCategory()
        ArrayList<Integer> child_cat_ids = new ArrayList<>(categoriesModel.getChild_categories());
        Integer[] ids = child_cat_ids.toArray(new Integer[child_cat_ids.size()]);

        RealmResults<CategoriesModel> subCategories = productListModel.getSubCategories(ids);
        view.refreshCategoryAdapter(new ArrayList<>(subCategories));

        // load products for this category
        if (categoriesModel.getId() != -1) { // if not clicked on ALL
            onCategorySelected(categoriesModel);
        } else { // in case of ALL
            getAllProductsAndDisplay();
        }
    }


    public void resetCategories() {
        onCategoriesReceived();
    }
}
