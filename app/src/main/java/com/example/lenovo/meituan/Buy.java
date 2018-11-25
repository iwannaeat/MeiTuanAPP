package com.example.lenovo.meituan;

public class Buy {
    private String goodsName;
    private double price;
    private String shopName;

    public Buy() {
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) { this.shopName = shopName; }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}