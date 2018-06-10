package com.ecom.ecomsat.homescreen.product_list.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.product_list.ProductListFragmentView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private ProductListFragmentView productListFragmentView;
    ArrayList<ProductsModel> data;

    public ProductsAdapter(ProductListFragmentView productListFragmentView) {
        this.productListFragmentView = productListFragmentView;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_product, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductsModel productsModel = data.get(position);
        holder.tvProductName.setText(productsModel.getName());
        holder.tvProductVariants.setText("Available variants: " + productsModel.getVariants().size());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refresh(ArrayList<ProductsModel> productsModels) {
        data = productsModels;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_variants)
        TextView tvProductVariants;
        @BindView(R.id.cv_product_container)
        CardView cvProductContainer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
