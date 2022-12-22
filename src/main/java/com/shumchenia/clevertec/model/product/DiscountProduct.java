package com.shumchenia.clevertec.model.product;

public class DiscountProduct extends ProductDecorator{

    private Double discount;
    public DiscountProduct(Product product,Double discount) {
        super(product);
        this.discount=discount;
    }

    @Override
    public String toString() {
        return super.toString()+" Акционный товар";
    }

    @Override
    public Double getPrice() {
        return super.getPrice()*(100-discount)/100;
    }
}
