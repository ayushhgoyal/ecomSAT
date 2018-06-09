package com.ecom.ecomsat.homescreen.models;

import io.realm.RealmList;

/**
 * Created by ayushgoyal on 07/06/18.
 */

public class ResponseModel {


    private RealmList<CategoriesModel> categories;
    private RealmList<RankingsModel> rankings;

    public RealmList<CategoriesModel> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<CategoriesModel> categories) {
        this.categories = categories;
    }

    public RealmList<RankingsModel> getRankings() {
        return rankings;
    }

    public void setRankings(RealmList<RankingsModel> rankings) {
        this.rankings = rankings;
    }


}
