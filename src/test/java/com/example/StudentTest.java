package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
       student = new Student("Вася");
        List.of(2, 3).forEach(student::addGrade);
    }

    /**
     * Набор тестов для проверки переопределения метода equals,
     * который должен возвращать при сравнении студентов:
     * 1. Сравнение студента Васи с самим собой считается equals->true
     * 2. Сравнение Васи с пустым местом (null) считается equals->false
     *      Вася не пустое место!
     * 2. Два объекта студента с именем Вася считаются одним человеком.
     *      Вася неповторим!
     * 3. Если у Васей разные оценки, то это разные люди
     * 4. Студенты с разными именами и одинаковыми оценками -- не один человек
     * 5. Сравнение с Object должно возвращать false
     */

    @DisplayName("Проверка: Сравнение студента Васи с самим собой считается equals->true")
    @Test
    void equalsReturnsTrueForSameObject() {
        student = new Student("Вася");
        assertTrue(student.equals(student), "Что то пошло не так, Вася оказался не в себе");
    }

    @DisplayName("Проверка, что Вася не пустое место")
    @Test
    void equalsReturnsFalseForNull() {
        student = new Student("Вася");
        assertFalse(student.equals(null), "Что то пошло не так, Вася оказался пустышкой");
    }

    @DisplayName("Проверка: Два студента с одинаковым именем и оценками считаются одним студентом")
    @Test
    void equalsReturnsTrueForStudentsWithSameNameAndGrades() {
        Student firstStudent = new Student("Вася");
        Student lastStudent = new Student("Вася");
        List.of(2, 5).forEach(firstStudent::addGrade);
        List.of(2, 5).forEach(lastStudent::addGrade);

        assertTrue(firstStudent.equals(lastStudent));
    }

    @DisplayName("Проверка: Два студента с одинаковым именем, но разными оценками не считаются одним человеком")
    @Test
    void equalsReturnsFalseForStudentWithSameNameAndDifferentGrades() {
        Student firstStudent = new Student("Вася");
        Student lastStudent = new Student("Вася");
        List.of(4, 5).forEach(firstStudent::addGrade);
        List.of(2, 3).forEach(lastStudent::addGrade);

        assertFalse(firstStudent.equals(lastStudent));
    }

    @DisplayName("Проверка: Студенты с разными именами и одинаковыми оценками -- не один человек")
    @Test
    void equalsReturnsFalseForStudentWithDifferentNames() {
        Student firstStudent = new Student("Вася");
        Student lastStudent = new Student("Коля");
        List.of(2, 3).forEach(firstStudent::addGrade);
        List.of(2, 3).forEach(lastStudent::addGrade);

        assertFalse(firstStudent.equals(lastStudent));
    }

    @DisplayName("Проверка: Сравнение с Object должно возвращать false")
    @Test
    void equalsReturnsFalseForObject() {
        assertFalse(student.equals("Вася"));
        assertFalse(student.equals(new Object()));
    }

    /**
     * Набор тестов для проверки переопределенного метода hashCode()
     * 1. Хэш-код одинаковый для студентов с одинаковым именем и оценками
     * 2. Хэш-код разный для студентов с одинаковым именем и разными оценками
     * 3. Хэщ-код разный для студентов с разными именами
     * 4. Проверка хэш-кода для одного объекта
     */
    @DisplayName("Хэш-код одинаковый для студентов с одинаковым именем и оценками")
    @Test
    void sameHashCodeForStudentsWithSameNameAndGrades() {
        Student firstStudent = new Student("Вася");
        Student lastStudent = new Student("Вася");
        List.of(2, 5).forEach(firstStudent::addGrade);
        List.of(2, 5).forEach(lastStudent::addGrade);

        assertEquals(firstStudent.hashCode(), lastStudent.hashCode());
    }

    @DisplayName("Хэш-код разный для студентов с одинаковым именем и разными оценками")
    @Test
    void differentHashCodeForStudentsWithSameNameAndDifferentGrades() {
        Student firstStudent = new Student("Вася");
        Student lastStudent = new Student("Вася");
        List.of(2, 4).forEach(firstStudent::addGrade);
        List.of(2, 5).forEach(lastStudent::addGrade);

        assertNotEquals(firstStudent.hashCode(), lastStudent.hashCode());
    }

    @DisplayName("Хэш-код разный для студентов с разными именами")
    @Test
    void differentHashCodeForStudentsWithDifferentName() {
        Student firstStudent = new Student("Вася");
        Student lastStudent = new Student("Коля");
        List.of(4, 2).forEach(firstStudent::addGrade);
        List.of(4, 2).forEach(lastStudent::addGrade);

        assertNotEquals(firstStudent.hashCode(), lastStudent.hashCode());
    }

    @DisplayName("Проверка что у одного объекта одинаковые хэши")
    @Test
    void hashCodeReturnsTrueForSameObject() {
        int hash1 = student.hashCode();
        int hash2 = student.hashCode();
        assertEquals(hash1, hash2);
    }

    /**
     * Набор тестов на проверку обновленной версии метода получения оценок
     * В которой убрана возможность напрямую изменять приватный список оценок в классе Student
     */

    @DisplayName("Проверка, что выбросится UnsupportedOperationException, если добавить элемент напрямую в список")
    @Test
    void addingToGradesListDirectlyThrowsUnsupportedOperationException() {
        List<Integer> grades = student.getGradesUnmodifiable();

        assertThrows(UnsupportedOperationException.class, () -> {
            grades.add(5);
        }, "Ожидалось UnsupportedOperationException, но что то пошло не так");
    }

    @DisplayName("Проверка, что выбросится UnsupportedOperationException, если изменить элемент по индексу")
    @Test
    void setGradeAtIndexThrowsUnsupportedOperationException() {
        List<Integer> grades = student.getGradesUnmodifiable();

        assertThrows(UnsupportedOperationException.class, () -> {
            grades.set(0, 2);
        }, "Ожидалось UnsupportedOperationException, но что то пошло не так");
    }

    @DisplayName("Проверка, что выбросится UnsupportedOperationException, если удалить элемент напрямую из списока")
    @Test
    void removingFromGradesListThrowsUnsupportedOperationException() {
        List<Integer> grades = student.getGradesUnmodifiable();

        assertThrows(UnsupportedOperationException.class, () -> {
            grades.remove(0);
        }, "Ожидалось UnsupportedOperationException, но что то пошло не так");
    }

    @DisplayName("Получить актуальный список через getGradesUnmodifiable()")
    @Test
    void getGradesUnmodifiableWorksCorrectly() {
        student.addGrade(4);
        List<Integer> grades = student.getGradesUnmodifiable();

        assertEquals(3, grades.size());
        assertEquals(List.of(2,3,4), grades);

    }

    @DisplayName("Каждый вызов getGradesUnmodifiable() возвращает новый объект")
    @Test
    void getGradesUnmodifiableReturnsNewInstanceEachTime() {
        List<Integer> firstGradesList = student.getGradesUnmodifiable();
        List<Integer> secondGradesList = student.getGradesUnmodifiable();

        assertNotSame(firstGradesList, secondGradesList);  // Проверяем, что ссылки на разные объекты
        assertEquals(firstGradesList, secondGradesList);     // Проверяем, что значение списков совпадает
    }

    /**
     * Попробовала через рефлексию обратиться к приватному полю name,
     * Чтобы проверить, что геттер меняет значение этого поля
     */
    @DisplayName("Проверить, что геттер возвращает значение приватного поля name через рефлексию")
    @Test
    void verifyGetNameMatchesPrivateNameField()  throws Exception {
        student = new Student("Barsik");
        Field privateField = Student.class.getDeclaredField("name");
        privateField.setAccessible(true);

        assertEquals(privateField.get(student), student.getName(),
                "Студента зовут: " + student.getName());
    }

// Тесты из презентации
    @DisplayName("Пример параметризированного теста на ввод невалидных оценок")
    @ParameterizedTest
    @MethodSource("com.example.GradeInvalidSource#ints")
    void testNotCorrectGrades(int x) {
        Student stud = new Student("vasia");
        assertThrows(IllegalArgumentException.class, () ->
                stud.addGrade(x));
    }

}

/**
 * Я добавила тест дату -5 для проверки отрицательных чисел в пример из урока
 */
class GradeInvalidSource{
    public static List<Integer> ints(){
        return List.of(0, 1, 6, 7, -5);
    }
}

