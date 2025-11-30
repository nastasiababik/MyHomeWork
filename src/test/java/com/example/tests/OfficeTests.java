package com.example.tests;

import office.Department;
import office.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.utils.OfficeTestsHelper.getEmployeeIdsByDepartment;
import static org.junit.jupiter.api.Assertions.*;

public class OfficeTests {
    private Connection con;

    @BeforeEach
    void setup() throws SQLException {
        con = DriverManager.getConnection("jdbc:h2:.\\Office");
        Service.createDB();

    }

    @AfterEach
    void tearDown() throws SQLException {
        if (con != null){}
            con.close();
    }

    @Test
    void checkEmployeeAfterRemoveDepartment() throws SQLException {
            int departmentId = 1;
            Department department = new Department(departmentId, "Accounting");

            // Подготовить sql запросы
        try(
                PreparedStatement selectEmployeByDepartment = con.prepareStatement("SELECT ID FROM Employee WHERE DepartmentID = ?");
                PreparedStatement countDepartmentById   = con.prepareStatement("SELECT COUNT(*) FROM Department WHERE ID = ?");
                PreparedStatement verifyEmployees = con.prepareStatement("SELECT ID, DepartmentID FROM Employee WHERE DepartmentID = ?")
                ) {

            //Из selectEmployeByDepartmen сохранить id сотрудников до удаления отдела
            List<Integer> employeeIdsBeforeRemovingDept = getEmployeeIdsByDepartment(selectEmployeByDepartment,  departmentId);

            // Удалить отдел
            Service.removeDepartment(department);

            // Проверить через SQL запрос countDepartmentById, что в таблице Department нет записей по ID удаленного отдела
            countDepartmentById.setInt(1, departmentId);
            ResultSet resultCountDepartmentById = countDepartmentById.executeQuery();
            while(resultCountDepartmentById.next()){
                assertEquals(0, resultCountDepartmentById.getInt(1));
            }

            //Добавить в таблицу новый отдел с id удаленного, но названием "QA"
            Service.addDepartment(new Department(departmentId, "QA"));

            // Получить список id сотрудников, которые состоят в новом отделе
            List<Integer> newDeptEmployeeIds = getEmployeeIdsByDepartment(verifyEmployees, departmentId);

            // Проверить содержание списка отдела
            assertFalse(employeeIdsBeforeRemovingDept.containsAll(newDeptEmployeeIds)
                    , "Данные сотрудников должны были удалиться, а они переехали в новый отдел");

        }
    }
}
