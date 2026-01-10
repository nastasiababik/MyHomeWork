package com.example.oop.products;

public abstract class Product {
    //поля-свойства
    private String name;
    private int price;

    // геттеры для доступа
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    //конструктор
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
