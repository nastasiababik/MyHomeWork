package com.example;

import lombok.*;

import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class StudentMock {
    @Setter
    @Getter
    private StudentRepo repo;

    @Getter
    @Setter
    private String name;
    private List<Integer> grades = new ArrayList<>();
    private List<Integer> marks = new ArrayList<>();

    public StudentMock(String name) {
        this.name = name;
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

    public List<Integer> getMarks() {
        return marks;
    }


    @SneakyThrows
    public int raiting() {
        return repo.getRaintingForGradeSum(
                marks.stream()
                        .mapToInt(x->x)
                        .sum()
        );
    }


}
