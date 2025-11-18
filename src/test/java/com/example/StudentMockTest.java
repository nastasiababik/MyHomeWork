package com.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StudentMockTest {

    /**
     *Тест на проверку того, что
     * правильные оценки попадают в список оценок, а неправильные нет.
    */

    @DisplayName("Проверка, что 2-5 оценки попадают в список оценок, а за границам диапазона нет")
    @Test
    public void testCheckGrade(){
        StudentMock stud = new StudentMock("Barsik", new GradeCheckerServiceMock());

        for (Integer grade : List.of(0,2, 1, 3,4,5, 7, 0, -2)) {
            try {
                stud.addGrade(grade);
            } catch (IllegalArgumentException e) {
                assertTrue(grade < 2 || grade > 5);
            }
        }

        List<Integer> grades = stud.getGrades();
        assertEquals(List.of(2,3,4,5), grades);

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
        StudentRepo repoMock = Mockito.mock(StudentRepo.class);
        StudentMock stud = new StudentMock("Barsik", null, new StudentRepositoryMock());
        Mockito.when(repoMock.getRatingForGradeSum(Mockito.anyInt())).thenReturn(10);
        stud.addMark(5);

        assertEquals(10,stud.rating());

    }

    @RepeatedTest(value=4, name="корректные оценки добавляются в список оценок")
    void markInRange(RepetitionInfo repetitionInfo) {
        StudentMock stud = new StudentMock("Barsik");
        int num= repetitionInfo.getCurrentRepetition()+1;
        stud.addMark(num);
        assertEquals(stud.getMarks().get(0), num);
    }

}

