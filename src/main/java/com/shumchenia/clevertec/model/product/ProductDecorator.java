package com.shumchenia.clevertec.model.product;

public class ProductDecorator extends Product{
    private Product product;

    public ProductDecorator(Product product) {
        this.product = product;
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public Double getPrice() {
        return product.getPrice();
    }
}
