package com.example;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StudentMockTest {


    /**
     * Тесты из лекции Архитектура заглушки
     */
    @DisplayName("Проверка рейтинга")
    @Test
    public void testRaiting(){
        StudentMock stud = new StudentMock("Barsik");
        stud.addMark(5);
        stud.setRepo(new StudentRepositoryMock());
        assertEquals(10, stud.raiting());

    }

    @DisplayName("Проверка рейтинга ипользуя Mockito")
    @Test
    public void testRaitingMockito(){
        StudentMock stud = new StudentMock("Barsik");
        stud.addMark(5);
        StudentRepo repo = Mockito.mock(StudentRepo.class);
        Mockito.when(repo.getRaintingForGradeSum(Mockito.anyInt())).thenReturn(10);
        stud.setRepo(repo);

        assertEquals(10,stud.raiting());

    }

    @RepeatedTest(value=4, name="корректные оценки добавляются в список оценок")
    void markInRange(RepetitionInfo repetitionInfo) {
        StudentMock stud = new StudentMock("vasia");
        int num= repetitionInfo.getCurrentRepetition()+1;
        stud.addMark(num);
        assertEquals(stud.getMarks().get(0), num);
    }

}

