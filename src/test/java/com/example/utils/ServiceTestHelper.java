package com.example.utils;

import office.Department;
import office.Service;
import office.dao.DepartmentDAO;
import office.dao.EmployeeDAO;

import java.sql.SQLException;
import java.util.List;

public class ServiceTestHelper {
    private final Service service;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;

    public ServiceTestHelper(Service service, DepartmentDAO departmentDAO, EmployeeDAO employeeDAO) {
        this.service = service;
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentDAO.getAllDepartments();
    }
}
