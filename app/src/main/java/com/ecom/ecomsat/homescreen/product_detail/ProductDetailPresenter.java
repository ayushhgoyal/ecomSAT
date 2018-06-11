package com.ecom.ecomsat.homescreen.product_detail;

import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.models.VariantsModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import io.realm.RealmList;

public class ProductDetailPresenter implements ProductDetailMVP.IProductDetailPresenter {


    private final ProductDetailModel productDetailModel;
    private ProductDetailFragmentView productDetailFragmentView;
    private ProductsModel product;
    private ArrayList<Integer> sizeList;

    public ProductDetailPresenter(ProductDetailFragmentView productDetailFragmentView, ProductsModel product) {
        this.productDetailFragmentView = productDetailFragmentView;
        this.product = product;
        productDetailModel = new ProductDetailModel();
    }

    @Override
    public void getProductDetails(String product_id) {
        productDetailModel.getProduct(product_id);
    }

    @Override
    public ArrayList<Integer> getSizeList() {
        RealmList<VariantsModel> variants = product.getVariants();
        LinkedHashSet<Integer> list = new LinkedHashSet<>();
        for (VariantsModel variant :
                variants) {
            int size = variant.getSize();
            list.add(size);
        }
        sizeList = new ArrayList<>(list);
        return sizeList;
    }

    @Override
    public ArrayList<String> getColorList() {

        RealmList<VariantsModel> variants = product.getVariants();
        HashSet<String> colors = new HashSet<>();
        for (VariantsModel variant :
                variants) {
            String color = variant.getColor();
            colors.add(color);
        }
        return new ArrayList<String>(colors);
    }

    /**
     * get colors available for this size and notify view
     *
     * @param position
     */
    @Override
    public void onSizeSelected(int position) {
        int size = sizeList.get(position);

        ArrayList<String> colorList = productDetailModel.getColorsForSize(product, size);
        productDetailFragmentView.setColorsAdapter(colorList);
    }
}
