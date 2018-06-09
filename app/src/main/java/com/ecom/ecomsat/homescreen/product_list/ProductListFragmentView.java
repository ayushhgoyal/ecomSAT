package com.ecom.ecomsat.homescreen.product_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecom.ecomsat.R;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListFragmentView extends Fragment implements ProductListMVP.IProductListView {
    public static final String TAG = "ProductListFragmentTag";
    private ProductListPresenter productListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        initPresenter();

        productListPresenter.getProducts();

        return view;
    }

    private void initPresenter() {
        productListPresenter = new ProductListPresenter(this);

    }
}
