package com.ecom.ecomsat.homescreen.product_list.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecom.ecomsat.R;
import com.ecom.ecomsat.homescreen.models.CategoriesModel;
import com.ecom.ecomsat.homescreen.product_list.ProductListFragmentView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ViewHolder> {

    private ProductListFragmentView productListFragmentView;
    ArrayList<CategoriesModel> data;

//    int selectedPosition = -1;

    public ParentCategoryAdapter(ProductListFragmentView productListFragmentView) {
        this.productListFragmentView = productListFragmentView;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_category, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCategoryName.setText(data.get(position).getName());

//        // set selection
//        holder.tvCategoryName.setTextColor(position == selectedPosition ? Color.BLUE : Color.BLACK);

        holder.cvCategoryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                productListFragmentView.onParentCategoryClicked(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refresh(ArrayList<CategoriesModel> categoriesModels) {
        data = categoriesModels;
//        selectedPosition = 0;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_name)
        TextView tvCategoryName;
        @BindView(R.id.cv_category_container)
        CardView cvCategoryContainer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
