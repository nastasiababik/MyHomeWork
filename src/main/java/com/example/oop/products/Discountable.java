package com.example.oop.products;


public interface Discountable {

    /**
     * Метод для подсчета финальной скидки
     * @param price цена
     * @return цена с учетом скидки
     */
    double discount(double price);
}
