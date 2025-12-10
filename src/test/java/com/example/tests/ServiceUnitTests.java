package com.example.tests;

import com.example.utils.ServiceTestHelper;
import office.Department;
import office.Employee;
import office.Service;
import office.dao.DepartmentDAO;
import office.dao.EmployeeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ServiceUnitTests {
    private Service service;
    private ServiceTestHelper helper;

    @Mock
    private DepartmentDAO departmentDAOMock;

    @Mock
    private EmployeeDAO employeeDAOMock;

    @BeforeEach
    void setUp() {
        service = new Service(departmentDAOMock, employeeDAOMock);
        helper = new ServiceTestHelper(service, departmentDAOMock, employeeDAOMock);
    }

    @DisplayName("После удаления отдела его нет в таблице Departments")
    @Test
    void testCheckDepartmentsListAfterRemovingDepartment() throws SQLException {
        System.setProperty("mockito.plugin.MockMaker", "mock-maker-default");

        Department department1 = new Department(1, "AccountingTest");
        Department department2 = new Department(2, "QATest");

        //Имитация через мок исходного содержимого таблицы Departments
        Mockito.when(departmentDAOMock.getAllDepartments()).thenReturn(List.of(department1, department2));

        //Вызов тестируемого метода удаления из Service
        service.removeDepartment(department1);

        //Имитация через мок изменения содержиого таблицы Departments
        Mockito.when(departmentDAOMock.getAllDepartments()).thenReturn(List.of(department2));

        List<Department> allDepartments = helper.getAllDepartments();

        assertFalse(allDepartments.contains(department1), "Удаленный отдел остался в таблице");
    }

    @DisplayName("Удаление отдела стриает данные связанных сотрудников")
    @Test
    void testDeletesEmployeesFromSameDepartment() throws SQLException {
        Department department1 = new Department(1, "AccountingTest");
        Department department2 = new Department(2, "QATest");
        Employee empl1 = new Employee(1, "Barsik", 1);
        Employee empl2 = new Employee(2, "Krosh", 2);

        Mockito.when(employeeDAOMock.getAllEmployee()).thenReturn(List.of(empl1, empl2));

        service.removeDepartment(department1);

        Mockito.verify(employeeDAOMock).delete(empl1);
        assertFalse(employeeDAOMock.getAllEmployee().contains(empl1));
        assertTrue(employeeDAOMock.getAllEmployee().contains(empl2));


    }


}
