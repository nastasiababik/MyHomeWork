package com.example.tests;

import java.util.List;

/**
 * Я добавила тест дату -5 для проверки отрицательных чисел в пример из урока
 */
class GradeInvalidSource {
    public static List<Integer> ints() {
        return List.of(0, 1, 6, 7, -5);
    }
}
