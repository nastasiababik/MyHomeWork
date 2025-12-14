package com.example.tests;

import office.Service;
import office.dao.DepartmentDAO;
import office.dao.EmployeeDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @Mock
    private DepartmentDAO departmentDAOMock;

    @Mock
    private EmployeeDAO employeeDAOMock;

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service(departmentDAOMock, employeeDAOMock);
    }

    @AfterEach
    void tearDown() {}


}
