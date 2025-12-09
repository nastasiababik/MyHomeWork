package office.dao;

import office.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {
    void createTable() throws SQLException;
    void dropTable() throws SQLException;
    void insert(Employee employee) throws SQLException;
    void delete(Employee employee) throws SQLException;

    // SELECT ID FROM Employee WHERE Name = ?
    List<Integer> getIdsByEmployeeName(String name) throws SQLException;

    // SELECT ID, Name FROM Employee
    List<Employee> getAllEmployee() throws SQLException;

    // UPDATE Employee SET Name = ? WHERE ID = ?
    void updateEmployeeNameById(int empId, String name) throws SQLException;

    // UPDATE Employee SET DepartmentId = ? WHERE ID = ?
    void setDepartmentByEmployeeId(int empId, int deptId) throws SQLException;

}
