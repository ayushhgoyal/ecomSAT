package com.ecom.ecomsat.homescreen.main;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class MainActivityMVP {
    public interface IMainActivityView {

    }

    public interface IMainActivityPresenter {
        public void addFragment();

        public void replaceFragment();
    }

    public interface IMainActivityModel {

    }
}
