package com.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class StudentMockTest {

    /**
     *Тесты на проверку того, что
     * правильные оценки попадают в список оценок, а неправильные нет.
    */


    @DisplayName("Проверка, что оценки попадают в список оценок, а неправильные нет")
    @Test
    public void testCheckGrade(){
        StudentMock stud = new StudentMock("Barsik", new GradeCheckerServiceMock());
        stud.addGrade(3);
        assertEquals(3,
                stud.getGrades().get(0));
    }

    /**
     * Тесты из лекции Архитектура заглушки
     */
    @DisplayName("Проверка рейтинга")
    @Test
    public void testRating(){
        StudentMock stud = new StudentMock("Barsik", null, new StudentRepositoryMock());
        stud.addMark(5);
        assertEquals(10, stud.rating());

    }

    @DisplayName("Проверка рейтинга ипользуя Mockito")
    @Test
    public void testRatingMockito(){
        StudentMock stud = new StudentMock("Barsik");
        stud.addMark(5);
        StudentRepo repo = Mockito.mock(StudentRepo.class);
        Mockito.when(repo.getRatingForGradeSum(Mockito.anyInt())).thenReturn(10);
        stud.setRepo(repo);

        assertEquals(10,stud.rating());

    }

    @RepeatedTest(value=4, name="корректные оценки добавляются в список оценок")
    void markInRange(RepetitionInfo repetitionInfo) {
        StudentMock stud = new StudentMock("vasia");
        int num= repetitionInfo.getCurrentRepetition()+1;
        stud.addMark(num);
        assertEquals(stud.getMarks().get(0), num);
    }

}

