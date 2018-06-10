package com.ecom.ecomsat.homescreen.product_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.product_list.adapters.CategoryAdapter;
import com.ecom.ecomsat.homescreen.product_list.adapters.ProductsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ayushgoyal on 08/06/18.
 */

public class ProductListFragmentView extends Fragment implements ProductListMVP.IProductListView {
    public static final String TAG = "ProductListFragmentTag";
    @BindView(R.id.rv_category_list)
    RecyclerView rvCategoryList;
    @BindView(R.id.rv_product_list)
    RecyclerView rvProductList;
    Unbinder unbinder;
    @BindView(R.id.cv_parent_category_container)
    CardView cvParentCategoryContainer;
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;
    private ProductListPresenter productListPresenter;

    private CategoryAdapter mCategoryAdapter;
    ProductsAdapter mProductsAdapter;
    private RecyclerView.LayoutManager mCategoryLayoutManager, mProductsLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        initPresenter();
        initAdapters();

        productListPresenter.getProducts();

        return view;
    }

    private void initAdapters() {
        mCategoryLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rvCategoryList.setLayoutManager(mCategoryLayoutManager);

        mProductsLayoutManager = new LinearLayoutManager(getActivity());
        rvProductList.setLayoutManager(mProductsLayoutManager);


        mCategoryAdapter = new CategoryAdapter(this);
        rvCategoryList.setAdapter(mCategoryAdapter);

        mProductsAdapter = new ProductsAdapter(this);
        rvProductList.setAdapter(mProductsAdapter);

    }

    private void initPresenter() {
        productListPresenter = new ProductListPresenter(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refreshCategoryAdapter(ArrayList<CategoriesModel> categoriesModels) {
        mCategoryAdapter.refresh(categoriesModels);

        // as appropriate categories are loaded,
        // load products related to category
        // first category products should be loaded

//        if (!categoriesModels.isEmpty()) {
//            productListPresenter.onCategorySelected(categoriesModels.get(0).getId());
//        }

    }

    @Override
    public void refreshProductsAdapter(ArrayList<ProductsModel> productsModels) {
        mProductsAdapter.refresh(productsModels);
    }

    @Override
    public void hideGoToParentButton() {
        cvParentCategoryContainer.setVisibility(View.GONE);
    }

    @Override
    public void showGoToParentButton() {
        cvParentCategoryContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setParentCategory(CategoriesModel parentCategory) {
        if (parentCategory != null) {
            tvCategoryName.setText(parentCategory.getName());
        }
    }

    public void onCategoryClicked(CategoriesModel categoriesModel) {
        productListPresenter.onCategorySelected(categoriesModel.getId());
    }
}
