package com.example.tests;

import office.Department;
import office.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            List<Integer> employeeIds = new ArrayList<>();
            selectEmployeByDepartment.setInt(1, departmentId);
            ResultSet resultSelectEmployeByDepartment = selectEmployeByDepartment.executeQuery();
            while(resultSelectEmployeByDepartment.next()){
                employeeIds.add(resultSelectEmployeByDepartment.getInt(1));
            }

            // Удалить отдел
            Service.removeDepartment(department);

            // Проверить через SQL запрос countDepartmentById, что в таблице Department нет записей по ID удаленного отдела
            countDepartmentById.setInt(1, departmentId);
            ResultSet resultCountDepartmentById = countDepartmentById.executeQuery();
            while(resultCountDepartmentById.next()){
                assertEquals(0, resultCountDepartmentById.getInt(1));
            }

            // Проверить через verifyEmployees, что остались сотрудники из удаленного отдела
            verifyEmployees.setInt(1, departmentId);
            ResultSet resultVerifyEmployees = verifyEmployees.executeQuery();
            List<Integer> lastEmployeeIds = new ArrayList<>();
            while(resultVerifyEmployees.next()){
                lastEmployeeIds.add(resultVerifyEmployees.getInt(1));
            }
            assertTrue(employeeIds.containsAll(lastEmployeeIds));

            //Добавить в таблицу новый отдел с id удаленного, но названием "QA"
            Department newDepartment = new Department(departmentId, "QA");
            Service.addDepartment(newDepartment);

            // Получить список id сотрудников, которые состоят в новом отделе
            verifyEmployees.setInt(1, departmentId);
            ResultSet resultEmployeesFromNewDepartment = verifyEmployees.executeQuery();
            List<Integer> newDepthEmployeeIds = new ArrayList<>();
            while (resultEmployeesFromNewDepartment.next()){
                newDepthEmployeeIds.add(resultEmployeesFromNewDepartment.getInt(1));
            }

            //Сравнить списки между собой -- если id сотрудников из удаленного отдела перекочевали в новый отдел, то тест упадёт. А он упадёт :)
            System.out.println(newDepthEmployeeIds);
            System.out.println(lastEmployeeIds);
            assertFalse(lastEmployeeIds.containsAll(newDepthEmployeeIds));

        }
    }
}
