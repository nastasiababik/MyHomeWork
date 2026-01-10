package com.example.oop.products;

public class PhysicalProduct extends Product{
    private int weight;

    public PhysicalProduct(String name, int price, int weight){
        super(name, price);
        this.weight = weight;

    }

    public int getWeight() {
        return weight;
    }
}
