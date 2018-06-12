package com.ecom.ecomsat.homescreen.product_list;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.product_list.adapters.CategoryAdapter;
import com.ecom.ecomsat.homescreen.product_list.adapters.ParentCategoryAdapter;
import com.ecom.ecomsat.homescreen.product_list.adapters.ProductsAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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
    //    @BindView(R.id.tv_category_name)
//    TextView tvCategoryName;
    @BindView(R.id.tv_current_category)
    TextView tvCurrentCategory;
    @BindView(R.id.fab_sort)
    FloatingActionsMenu fabSort;
    @BindView(R.id.rv_parent_category_list)
    RecyclerView rvParentCategoryList;
    @BindView(R.id.empty_loading_view)
    RelativeLayout emptyLoadingView;
    //    @BindView(R.id.action_most_viewed)
//    FloatingActionButton actionMostViewed;
//    @BindView(R.id.action_most_shared)
//    FloatingActionButton actionMostShared;
//    @BindView(R.id.action_most_ordered)
//    FloatingActionButton actionMostOrdered;
//    @BindView(R.id.action_all)
//    FloatingActionButton actionAll;
    private ProductListPresenter productListPresenter;

    IProductListInteractionListerner iProductListInteractionListerner;

    private CategoryAdapter mCategoryAdapter;
    ProductsAdapter mProductsAdapter;
    private RecyclerView.LayoutManager mCategoryLayoutManager, mProductsLayoutManager;
    private ParentCategoryAdapter mParentCategoryAdapter;

    public CategoriesModel getParentCategory() {
        return parentCategory;
    }

    private CategoriesModel parentCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        initPresenter();
        initAdapters();
//        goToParentClickListener();
        initSortFab();

        productListPresenter.getProducts();

        return view;
    }

    private void initSortFab() {

        ArrayList<String> rankings = productListPresenter.getRankings();

        FloatingActionButton actionAllButton = new FloatingActionButton(getActivity());
        actionAllButton.setTitle("Reset");
        fabSort.addButton(actionAllButton);


        actionAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabSort.collapse();
                productListPresenter.resetCategories();
            }
        });

        for (String rank :
                rankings) {
            FloatingActionButton floatingActionButton = new FloatingActionButton(getActivity());
            floatingActionButton.setTitle(rank);
            fabSort.addButton(floatingActionButton);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fabSort.collapse();
                    productListPresenter.getProductsForRank(rank);
                }
            });
        }

    }

//    private void goToParentClickListener() {
//        cvParentCategoryContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getParentCategory();
//                productListPresenter.resetCategories();
////                hideGoToParentButton();
//            }
//        });
//    }

    private void initAdapters() {

        LinearLayoutManager mParentategoryLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rvParentCategoryList.setLayoutManager(mParentategoryLayoutManager);

        mCategoryLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rvCategoryList.setLayoutManager(mCategoryLayoutManager);

        mProductsLayoutManager = new LinearLayoutManager(getActivity());
        rvProductList.setLayoutManager(mProductsLayoutManager);

        mParentCategoryAdapter = new ParentCategoryAdapter(this);
        rvParentCategoryList.setAdapter(mParentCategoryAdapter);

        mCategoryAdapter = new CategoryAdapter(this);
        mProductsAdapter = new ProductsAdapter(this);

        rvCategoryList.setAdapter(mCategoryAdapter);

//        rvParentCategoryList.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });

//        rvCategoryList.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
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
    public void refreshProductsAdapter(ArrayList<ProductsModel> productsModels, String name) {
        tvCurrentCategory.setText("Displaying " + productsModels.size() + " products for: " + name);
        mProductsAdapter.refresh(productsModels);
    }

//    @Override
//    public void hideGoToParentButton() {
//        cvParentCategoryContainer.setVisibility(View.GONE);
//    }

    @Override
    public void showGoToParentButton() {
//        cvParentCategoryContainer.setVisibility(View.VISIBLE);
    }


    @Override
    public void onProductListitemClick(int adapterPosition) {
        productListPresenter.onProductClicked(adapterPosition);
    }

    @Override
    public void launchProductDetail(ProductsModel product) {
        iProductListInteractionListerner.launchProductDetail(product);
    }

    @Override
    public void onParentCategoryClicked(CategoriesModel categoriesModel) {
        productListPresenter.onParentCategorySelected(categoriesModel);
    }

    @Override
    public void refreshParentCategoryAdapter(ArrayList<CategoriesModel> parentCategoryModels) {
        mParentCategoryAdapter.refresh(parentCategoryModels);
        rvParentCategoryList.smoothScrollToPosition(parentCategoryModels.size());
    }

    @Override
    public void showLoadingView() {
        emptyLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        emptyLoadingView.setVisibility(View.GONE);
    }

    public void onCategoryClicked(CategoriesModel categoriesModel) {
        productListPresenter.onCategorySelected(categoriesModel);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iProductListInteractionListerner = (IProductListInteractionListerner) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
