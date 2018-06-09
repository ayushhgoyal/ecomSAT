package com.ecom.ecomsat.homescreen.main;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class MainActivityMVP {
    public interface IMainActivityView {
        /**
         * Displays loading view with general message
         */
        public void showLoadingView();

        public void hideLoadingView();

        /**
         * Displays loading view with specific message
         *
         * @param message
         */
        public void showLoadingView(String message);

        public void showErrorMessage(String message);
    }

    public interface IMainActivityPresenter {
        public void addFragment();

        public void replaceFragment();
    }

    public interface IMainActivityModel {

    }
}
