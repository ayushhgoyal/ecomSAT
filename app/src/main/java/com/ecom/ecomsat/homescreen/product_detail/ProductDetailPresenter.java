package com.ecom.ecomsat.homescreen.product_detail;

import com.ecom.ecomsat.homescreen.models.ProductsModel;
import com.ecom.ecomsat.homescreen.models.VariantsModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import io.realm.RealmList;

public class ProductDetailPresenter implements ProductDetailMVP.IProductDetailPresenter {


    private final ProductDetailModel productDetailModel;
    private ProductDetailFragmentView productDetailFragmentView;
    private ProductsModel product;
    private ArrayList<Integer> sizeList;
    private Integer selectedSize;
    private ArrayList<String> availableColorList;

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

            /**
             * since realm treats null as 0
             */
            if (size != 0) {
                list.add(size);
            }

        }
        sizeList = new ArrayList<>(list);
        return sizeList;
    }


    /**
     * get colors available for this selectedSize and notify view
     *
     * @param position
     */
    @Override
    public void onSizeSelected(int position) {
        selectedSize = sizeList.get(position);

        getColorsForSize(selectedSize);
    }

    private void getColorsForSize(Integer selectedSize) {
        availableColorList = productDetailModel.getColorsForSize(product, selectedSize);

        productDetailFragmentView.setColorsAdapter(availableColorList);
    }

    /**
     * Since size and color are selected
     * now find corresponding price
     * and update amount with tax
     *
     * @param adapterPosition
     */
    @Override
    public void onColorSelected(int adapterPosition) {
        String color = availableColorList.get(adapterPosition);

        if (selectedSize == -1) { // size not applicable
            getVariantForColorOnly(color);
        } else { // size applicable
            getVariantForColorAndSize(color);
        }

    }

    private void getVariantForColorOnly(String color) {
        VariantsModel variantsModel = productDetailModel.getProductVariant(product, color);
        calculateAmounts(variantsModel);
    }

    private void getVariantForColorAndSize(String color) {
        VariantsModel productVariant = productDetailModel.getProductVariant(product, selectedSize, color);

        calculateAmounts(productVariant);
    }

    private void calculateAmounts(VariantsModel productVariant) {
        int price = productVariant.getPrice();
        double tax = product.getTax().getValue();

        double total_amount = price + (price * (tax / 100));

        productDetailFragmentView.setAmounts(price, tax, total_amount);
    }

    @Override
    public void onSizeNotApplicable() {

        selectedSize = -1;

        // get all colors and proceed

        availableColorList = productDetailModel.getAllColors(product);
        productDetailFragmentView.setColorsAdapter(availableColorList);
    }
}
