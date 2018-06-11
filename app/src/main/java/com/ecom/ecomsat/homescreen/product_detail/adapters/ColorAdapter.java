package com.ecom.ecomsat.homescreen.product_detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.product_detail.ProductDetailFragmentView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private final ProductDetailFragmentView productDetailFragmentView;
    private ArrayList<String> colorList;

    public ColorAdapter(ProductDetailFragmentView productDetailFragmentView, ArrayList<String> colorList) {
        this.productDetailFragmentView = productDetailFragmentView;
        this.colorList = colorList;
    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_product_property, parent, false);
        ColorAdapter.ViewHolder vh = new ColorAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvProperty.setText(colorList.get(position));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_property)
        TextView tvProperty;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
