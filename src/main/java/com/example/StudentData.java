package com.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StudentData {
    @Setter @Getter
    private Integer id; // id может быть null
    @Setter @Getter
    private String name; // Обязательное поле
    private List<Integer> marks; //может быть пустым, содержать числа или быть null.

    public StudentData() {};

    public StudentData(String name) {
        this.name = name;
    }

    public StudentData(String name, List<Integer> marks) {
        this.name = name;
        this.marks = marks;
    }

    public StudentData(Integer id, String name, List<Integer> marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public List<Integer> getMarks() {
        return Collections.unmodifiableList(marks);

        // Возвращаем неизменяемый список или null
        /**
        return (marks != null)
                ? Collections.unmodifiableList(marks)
                : null;
         */
    }

    public void addMark(int mark) {
        if (mark < 2 || mark > 5) {
            throw new IllegalArgumentException(mark + " is wrong mark");
        }
        marks.add(mark);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.marks);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentData other = (StudentData) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.marks, other.marks);
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", marks=" + marks + '}';
    }



}
