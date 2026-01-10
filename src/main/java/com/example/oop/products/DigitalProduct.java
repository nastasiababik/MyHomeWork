package com.example.oop.products;

public class DigitalProduct extends Product{
    private int fileSize;

    //переопределить вызов конструктора - вызываем конструктор родителя ключевое super
    //чтобы инициализировать все поля даже приватные род класса (соблюдаем контракт)
    //принцип инкапсуляции: делегируем родительскому классу создание его потомка (используем готовую реализацию)
    //дочерний зависит от абстракции родителя
    public DigitalProduct(String name, int price, int fileSize){
        //super вызов родительского конструктора
        super(name, price);
        //this обращение к полю текущего экземпляра класса (а не родителя)
        this.fileSize = fileSize;

    }

    public int getFileSize() {
        return fileSize;
    }
}
