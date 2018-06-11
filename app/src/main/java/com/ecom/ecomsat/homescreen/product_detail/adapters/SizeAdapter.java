package com.ecom.ecomsat.homescreen.product_detail.adapters;

import android.graphics.Color;
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

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    private final ProductDetailFragmentView productDetailFragmentView;
    private final ArrayList<Integer> sizeList;
    int selectedPosition = -1;

    public SizeAdapter(ProductDetailFragmentView productDetailFragmentView, ArrayList<Integer> sizeList) {
        this.productDetailFragmentView = productDetailFragmentView;
        this.sizeList = sizeList;
    }

    @Override
    public SizeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_product_property, parent, false);
        SizeAdapter.ViewHolder vh = new SizeAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvProperty.setText(sizeList.get(position) + "");

        holder.tvProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDetailFragmentView.onSizeClicked(holder.getAdapterPosition());
            }
        });

        // highlight selection
        holder.tvProperty.setTextColor(position == selectedPosition ? Color.RED : Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public void setSelected(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
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
