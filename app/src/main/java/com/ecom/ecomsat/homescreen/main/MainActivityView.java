package com.ecom.ecomsat.homescreen.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.product_list.ProductListFragmentView;

public class MainActivityView extends AppCompatActivity implements MainActivityMVP.IMainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProductListFragment();
    }

    private void loadProductListFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new ProductListFragmentView()).commit();

    }

    /**
     * Displays loading view with general message
     */
    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    /**
     * Displays loading view with specific message
     *
     * @param message
     */
    @Override
    public void showLoadingView(String message) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
