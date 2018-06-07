package com.ecom.ecomsat.homescreen.models;

import java.util.List;

/**
 * Created by ayushgoyal on 07/06/18.
 */

public class ResponseModel {


    private List<CategoriesModel> categories;
    private List<RankingsModel> rankings;

    public List<CategoriesModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesModel> categories) {
        this.categories = categories;
    }

    public List<RankingsModel> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingsModel> rankings) {
        this.rankings = rankings;
    }


}
