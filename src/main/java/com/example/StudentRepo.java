package com.example;

public interface StudentRepo {
    int getRaintingForGradeSum(int sum); // Получить рейтинг студента на основе числа
    long count(); //Посчитать кол-во студентов в репозитории
    void delete(Student entity); //Удалить студента
    void deleteAll(Iterable<Student> entities); //Удалить всех студентов
    Iterable<Student> findAll(); // Найти всех студентов
    Student save(Student entity); //Сохранить студента
    Iterable<Student> saveAll(Iterable<Student> entities); //Сохранить всех студентов
}

