package com.example;

public class GradeCheckerServiceMock implements GradeCheckerService{
    public boolean isValidGrade(int grade) {
        return grade >= 2 && grade <= 5;
    }

}
