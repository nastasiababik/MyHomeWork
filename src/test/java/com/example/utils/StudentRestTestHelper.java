package com.example.utils;

import com.example.model.StudentData;
import com.example.model.StudentResponse;
import com.example.requests.StudentApi;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.example.requests.StudentApi.postStudent;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentRestTestHelper {

    /**
     * Создает студента в бд приложения через post запрос
     * @param studentId может быть числом или null
     * @param studentName должно быть заполнено обязательно
     * @param marks список оценок может быть пустым, содержать числа
     * @return Возвращает объект студента тип StudentData
     * или быть null.
     */
    public static StudentData createStudentDB(Integer studentId, String studentName, List<Integer> marks) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя студента не может быть null или пустым");
        }
        List<Integer> listMark = (marks != null)
                ? new ArrayList<>(marks)
                : null;

        StudentData student = new StudentData(studentId, studentName, listMark);

        postStudent(student);

        return student;
    }

    /**
     * Метод сравниваем имя и оценки студента с данными, которые вернул метод StudentApi
     * @param student эталонные данные студента
     *  @param payload payload для сравнения
     */
    public static void assertStudentDataEquals(StudentData student, StudentResponse payload){
        assertEquals(student.getId(), payload.getId());
        assertEquals(student.getName(), payload.getName());
        assertEquals(student.getMarks(), payload.getMarks());
    }

    /**
     * Метод сравниваем имя и оценки студента с данными, которые вернул метод StudentApi
     * @param student эталонные данные студента
     * @param response запрос, из которого извлекается payload для сравнения
     */
    public static void assertNameAndMarksMatch(StudentData student, Response response){
        StudentResponse payload = response.body().as(StudentResponse.class, ObjectMapperType.JACKSON_2);
        assertEquals(student.getName(), payload.getName());
        assertEquals(student.getMarks(), payload.getMarks());
    }

    /**
     Метод, который извлекает payload из респонса и сравнивает его с созданным студентом.
     */
    public static void assertResponseMatchesStudent(Response response, StudentData student){
        StudentResponse payload = response.body().as(StudentResponse.class, ObjectMapperType.JACKSON_2);
        assertStudentDataEquals(student, payload);
    }

    /**
     * Получить список топовых студентов
     * @return Возвращает список топовых студентов или пустой список, если ни один студент не попал в топ
     */
   public static List<StudentResponse> getListTopStudents() {
        Response response = StudentApi.topStudent();
        assertEquals(200, response.getStatusCode());
        return response.body()
                .jsonPath()
                .getList(".", StudentResponse.class);
    }

}
