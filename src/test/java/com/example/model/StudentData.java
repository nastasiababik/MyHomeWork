package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class StudentData {
    @JsonProperty
    private Integer id; // id может быть null

    @JsonProperty
    private String name; // Обязательное поле

    @JsonProperty
    private List<Integer> marks; //может быть пустым, содержать числа или быть null.

    //Конструкторы
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
            if (marks == null) {
                return null;
            }
            return Collections.unmodifiableList(marks);
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
        hash = 13 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
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
