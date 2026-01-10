package com.example.oop.products;

public class PercentageDiscount implements Discountable {
    private int percentage;

    public PercentageDiscount(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public double discount(double price){
        return price * percentage / 100;
    }
}
