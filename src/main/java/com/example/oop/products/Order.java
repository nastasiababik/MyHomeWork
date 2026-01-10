package com.example.oop.products;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private List<Product> products;
    private List<Discountable> discounts;

    public Order() {
        this.products = new LinkedList<>();
        this.discounts = new LinkedList<>();
    }

    public Order(List<Product> products, List<Discountable> discounts) {
        this.products = new LinkedList<>();
        this.discounts = new LinkedList<>();
    }

    public void addProduct(Product item){
        this.products.add(item);
    }

    public void addProducts( List<Product> products){
        this.products.addAll(products);
    }

    public void addDiscount(Discountable sale){
        this.discounts.add(sale);
    }

    public void addDiscounts(List<Discountable> discounts){
        this.discounts.addAll(discounts);
    }

    public double calcTotalPrice(){
        double totalPrice = 0.0;

        //суммировать стоимость товаров
        for(Product item : products){
            totalPrice += item.getPrice();
        }

        //применить скидки
        for(Discountable sale : discounts){
            totalPrice -= sale.discount(totalPrice);
        }

        return totalPrice;

    }


}
