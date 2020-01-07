package com.rujal.hamrobazaar;

public class Advertisement {

    private String name;
    private String price;
    private int imageId;
    private String productType;

    public Advertisement(String name, String price, int imageId, String productType) {
        this.name = name;
        this.price = price;
        this.imageId = imageId;
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

}
