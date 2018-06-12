package com.ecom.ecomsat.homescreen.product_list;

import android.util.Log;

import com.ecom.ecomsat.common.MyApplication;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.models.RankedProductModel;
import com.ecom.ecomsat.homescreen.models.RankingsModel;
import com.ecom.ecomsat.homescreen.models.ResponseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListModel implements ProductListMVP.IProductListModel {


    private MyApplication myApplication;
    private ProductListPresenter productListPresenter;

    Realm realm = Realm.getDefaultInstance();

    @Inject
    Call<ResponseModel> productListCall;

    public ProductListModel(MyApplication myApplication, ProductListPresenter productListPresenter) {
        this.myApplication = myApplication;
        this.productListPresenter = productListPresenter;
        myApplication.getDaggerApiComponent().inject(this);
    }

    /**
     * returns DB data is exists
     * and continues to update from server
     */
    @Override
    public void fetchProductList() {

        if (realm.where(ProductsModel.class).findFirst() != null) {
            Log.d("ProductListModel", "Data exists");
            productListPresenter.onCategoriesReceived();
        } else {
            Log.d("ProductListModel", "Data doesn't exist");
        }


        productListCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(responseModel.getCategories());
                        realm.copyToRealmOrUpdate(responseModel.getRankings());
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d("ProductListModel",
                                "realm.where(CategoriesModel.class).findAll():"
                                        + realm.where(CategoriesModel.class).findAll().size());

                        productListPresenter.onCategoriesReceived();
                    }
                });


            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }


    @Override
    public ArrayList<String> getRankings() {
        RealmResults<RankingsModel> all = realm.where(RankingsModel.class).distinct("ranking").findAll();
        ArrayList<String> strings = new ArrayList<>();
        for (RankingsModel rankingsModel :
                all) {
            strings.add(rankingsModel.getRanking());
        }
        return strings;
    }

    @Override
    public HashSet<ProductsModel> getProductsForRank(String rank) {

        // fetch product ids for this rank

        RealmResults<RankedProductModel> products = realm
                .where(RankingsModel.class)
                .equalTo("ranking", rank)
                .findFirst().getProducts().where()
                .sort("shares", Sort.DESCENDING).findAll()
                .where()
                .sort("view_count", Sort.DESCENDING).findAll()
                .where()
                .sort("order_count", Sort.DESCENDING).findAll();

        // these fields should not be hardcoded :(

//        /**
//         * get ids for these models
//         * using linked hash set to maintain insertion order
//         */
//
//        LinkedHashSet<Integer> product_ids = new LinkedHashSet<>();
//
//        for (RankedProductModel rankedProductModel :
//                products) {
//            product_ids.add(rankedProductModel.getId());
//        }
//
//
//        // get product models for these ids
//
//        Integer[] ids = product_ids.toArray(new Integer[product_ids.size()]);
//
//        /*************ERROR*************
//         * following is NOT keeping the same order so we
//         * need to get each product by ourselves
//         *
//         * This step and previous one can be skipped (Optional)
//         */
//        RealmResults<ProductsModel> productModels = realm.where(ProductsModel.class)
//                .in("id",
//                        ids).findAll();

//        querying on returned results only

        RealmResults<ProductsModel> realmResults = realm.where(ProductsModel.class).findAll();

        LinkedHashSet<ProductsModel> productsModelHashSet = new LinkedHashSet<>();
        for (RankedProductModel rankedProductModel :
                products) {
            ProductsModel productsModel = realmResults.where()
                    .equalTo("id", rankedProductModel.getId()).findFirst();

            // set count as well
            // since only one count will have valid value and other as 0
            // so its safe to add them
            // in order to generalize this method

            productsModel.setCount("Count: " + (rankedProductModel.getOrder_count()
                    + rankedProductModel.getShares() + rankedProductModel.getView_count()
            ));


            productsModelHashSet.add(productsModel);
        }


        return productsModelHashSet;

    }

    @Override
    public RealmResults<CategoriesModel> getSubCategories(Integer[] ids) {
        RealmResults<CategoriesModel> categoriesModels = realm.where(CategoriesModel.class).in("id", ids).findAll();
        return categoriesModels;
    }

    @Override
    public ArrayList<ProductsModel> getAllProducts() {
        return new ArrayList<>(realm.where(ProductsModel.class).findAll());
    }

    /**
     * Fetches all products for this category and its all child categories
     *
     * @param id
     * @return
     */
    public ArrayList<ProductsModel> getProductsForCategory(int id) {

        ArrayList<ProductsModel> productsModels = new ArrayList<>();

        CategoriesModel categoryModel = realm.where(CategoriesModel.class)
                .equalTo("id", id).findFirst();

        // get all products for this passed category
        productsModels.addAll(categoryModel.getProducts());

        // now get all underlying child categories for this category and
        // then fetch all products for all these categories
        // and keep saving them in same arraylist - finally send it across to shine


        if (!categoryModel.getChild_categories().isEmpty()) { // if child categories are present
            for (CategoriesModel model :
                    getSubCategoriesForCategory(categoryModel.getId())) {
                productsModels.addAll(getProductsForCategory(model.getId()));
            }
        }


        return productsModels;
    }

    /**
     * Fetches child categories of passed category - only one level deep
     *
     * @param id
     * @return
     */
    public ArrayList<CategoriesModel> getSubCategoriesForCategory(int id) {
        List<Integer> child_cat_ids = realm.where(CategoriesModel.class)
                .equalTo("id", id).findFirst().getChild_categories();

        Integer[] ids = child_cat_ids.toArray(new Integer[child_cat_ids.size()]);

        ArrayList<CategoriesModel> child_cat_models = new ArrayList<CategoriesModel>
                (realm.where(CategoriesModel.class).in("id", ids).findAll());

        return child_cat_models;
    }
}
