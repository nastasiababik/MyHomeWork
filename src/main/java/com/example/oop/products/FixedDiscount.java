package com.example.oop.products;

public class FixedDiscount implements Discountable{
    private int fixSum;

    public FixedDiscount(int fixSum) {
        this.fixSum = fixSum;
    }

    public int getFixSum() {
        return fixSum;
    }

    @Override
    public double discount(double price){
        return fixSum;
    }
}
