package com.example.tests;

import office.*;
import office.dao.DepartmentDAO;
import office.dao.EmployeeDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import java.util.List;

import static com.example.utils.OfficeTestsHelper.getEmployeeIdsByDepartment;
import static org.junit.jupiter.api.Assertions.*;

public class OfficeTests {
    private Service service;
    private H2DbConnector dbConnector;


    @BeforeEach
    void setup() throws SQLException {
        dbConnector = new H2DbConnector("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;"); // Для теста заменила настоящую бд на храняющуся в оперативной памяти
        DepartmentDAO departmentDAO = new H2DepartmentDAO(dbConnector);
        EmployeeDAO employeeDAO = new H2EmployeeDAO(dbConnector);
        service = new Service(departmentDAO, employeeDAO);

        service.createDB();
    }

    @AfterEach
    void tearDown() throws SQLException {
    }

    @DisplayName("Удаление отдела удаляет и информацию о сотрудниках в таблице Employee")
    @Test
    void checkEmployeeAfterRemoveDepartment() throws SQLException {
            int departmentId = 1;
            Department department = new Department(departmentId, "Accounting");

            // Подготовить sql запросы
        try(
                Connection con = dbConnector.getConnection();
                PreparedStatement selectEmployeByDepartment = con.prepareStatement("SELECT ID FROM Employee WHERE DepartmentID = ?");
                PreparedStatement countDepartmentById   = con.prepareStatement("SELECT COUNT(*) FROM Department WHERE ID = ?");
                PreparedStatement verifyEmployees = con.prepareStatement("SELECT ID, DepartmentID FROM Employee WHERE DepartmentID = ?")
                ) {

            //Из selectEmployeByDepartmen сохранить id сотрудников до удаления отдела
            List<Integer> employeeIdsBeforeRemovingDept = getEmployeeIdsByDepartment(selectEmployeByDepartment,  departmentId);

            // Удалить отдел
            service.removeDepartment(department);

            // Проверить через SQL запрос countDepartmentById, что в таблице Department нет записей по ID удаленного отдела
            countDepartmentById.setInt(1, departmentId);
            ResultSet resultCountDepartmentById = countDepartmentById.executeQuery();
            while(resultCountDepartmentById.next()){
                assertEquals(0, resultCountDepartmentById.getInt(1));
            }

            //Добавить в таблицу новый отдел с id удаленного, но названием "QA"
            service.addDepartment(new Department(departmentId, "QA"));

            //Проверить, что в таблице Employee не осталось сотрудников связанных с удаленным отделом
            verifyEmployees.setInt(1, departmentId);
            ResultSet resultVerifyEmployees = verifyEmployees.executeQuery();
            assertFalse(resultVerifyEmployees.next(), "Данные сотрудников не удалились");

        }
    }
}
