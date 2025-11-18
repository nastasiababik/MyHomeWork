package com.example;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class StudentMock {
    @Setter @Getter
    private String name;
    @Setter @Getter
    private StudentRepo repo;
    @Setter @Getter
    private GradeCheckerService gradeChecker;     // Поле сервиса проверки оценок

    private List<Integer> marks = new ArrayList<>();
    private List<Integer> grades = new ArrayList<>();

    // Конструктор только с именем (gradeChecker будет null)
    public StudentMock(String name) {
        this.name = name;
        this.gradeChecker = null;
        this.repo = null;

    }

    // Конструктор с обоими параметрами
    public StudentMock(String name, GradeCheckerService gradeChecker) {
        this.name = name;
        this.gradeChecker = gradeChecker;
        this.repo = null;
    }

    // Конструктор с тремя параметрами
    public StudentMock(String name, GradeCheckerService gradeChecker, StudentRepo repo) {
        this.name = name;
        this.gradeChecker = gradeChecker;
        this.repo = repo;
    }

    public List<Integer> getMarks() {
        return new ArrayList<>(marks);
    }
    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    public void addMark(int mark) {
        if (mark < 2 || mark > 5) {
            throw new IllegalArgumentException(mark + " is wrong mark");
        }
        marks.add(mark);
    }

    //Использую isValidateGrade из заглушки сервиса GradeCheckerServiceMock
    @SneakyThrows
    public void addGrade(int grade) {
        System.out.println("grades = " + grades);
        if (!gradeChecker.isValidGrade(grade)) { //Проверка через метод мока
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
        grades.add(grade);

    }

    @SneakyThrows
    public int rating() {
        return repo.getRatingForGradeSum(
                marks.stream()
                        .mapToInt(x->x)
                        .sum()
        );
    }

}
