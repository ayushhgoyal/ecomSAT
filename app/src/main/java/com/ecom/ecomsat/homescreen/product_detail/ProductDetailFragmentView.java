package com.ecom.ecomsat.homescreen.product_detail;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.main.MainActivityView;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.product_detail.adapters.ColorAdapter;
import com.ecom.ecomsat.homescreen.product_detail.adapters.SizeAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragmentView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragmentView extends Fragment implements ProductDetailMVP.IProductDetailView {
    private static final String ARG_PRODUCT_JSON = "product_json";


    @BindView(R.id.rv_size)
    RecyclerView rvSize;
    @BindView(R.id.container_size)
    RelativeLayout containerSize;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_date_added)
    TextView tvDateAdded;
    @BindView(R.id.rv_color)
    RecyclerView rvColor;
    @BindView(R.id.label_color)
    TextView labelColor;
    @BindView(R.id.container_color)
    RelativeLayout containerColor;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.label_tax)
    TextView labelTax;
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    Unbinder unbinder;

    private String product_json;
    private ProductDetailPresenter productDetailPresenter;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    private ArrayList<Integer> sizeList;

    public ProductsModel getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(ProductsModel productsModel) {
        this.productsModel = productsModel;
    }

    ProductsModel productsModel;

    Gson gson = new Gson();


    public ProductDetailFragmentView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param product_json
     * @return A new instance of fragment ProductDetailFragmentView.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragmentView newInstance(String product_json) {
        ProductDetailFragmentView fragment = new ProductDetailFragmentView();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_JSON, product_json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            product_json = getArguments().getString(ARG_PRODUCT_JSON);
//            setProductsModel(gson.fromJson(product_json, ProductsModel.class));
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        setProductsModel(((MainActivityView) getActivity()).getProduct());

        productDetailPresenter = new ProductDetailPresenter(this,
                getProductsModel());

        setProductDetails();


        return view;
    }

    private void setProductDetails() {
        tvProductName.setText(getProductsModel().getName());
        tvDateAdded.setText("Added on: " + getProductsModel().getDate_added());
        labelTax.setText("Tax (" + getProductsModel().getTax().getName() + "):");

        initColorAdapter();
        initSizeAdapter();
    }

    private void initColorAdapter() {

        LinearLayoutManager mHorizontalLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rvColor.setLayoutManager(mHorizontalLayoutManager);


    }

    private void initSizeAdapter() {

        sizeList = productDetailPresenter.getSizeList();

        LinearLayoutManager mHorizontalLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rvSize.setLayoutManager(mHorizontalLayoutManager);

        sizeAdapter = new SizeAdapter(this, sizeList);
        rvSize.setAdapter(sizeAdapter);

        // consider first item clicked
        onSizeClicked(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSizeClicked(int position) {
        sizeAdapter.setSelected(position);
        productDetailPresenter.onSizeSelected(position);
    }

    @Override
    public void setColorsAdapter(ArrayList<String> colorList) {
        colorAdapter = new ColorAdapter(this, colorList);
        rvColor.setAdapter(colorAdapter);

        // consider first color clicked
        onColorClicked(0);
    }

    @Override
    public void onColorClicked(int adapterPosition) {
        colorAdapter.setSelected(adapterPosition);
        productDetailPresenter.onColorSelected(adapterPosition);
    }

    @Override
    public void setAmounts(int price, double tax, double total_amount) {
        tvPrice.setText(price + "");
        tvTax.setText(tax + "");
        tvTotalAmount.setText(total_amount + "");
    }
}
