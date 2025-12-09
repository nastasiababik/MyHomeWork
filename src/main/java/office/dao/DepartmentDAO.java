package office.dao;

import office.Department;

import java.sql.SQLException;

public interface DepartmentDAO {
    void createTable() throws SQLException;
    void dropTable() throws SQLException;
    void insert(Department department) throws SQLException;
    void delete(Department department) throws SQLException;

    /*
    SELECT COUNT(*) AS EmployeeCount  FROM Employee JOIN Department ON Employee.DepartmentID = Department.ID  WHERE Department.Name = ?
     */
    int getEmployeeCountByDepartmentName(String name) throws SQLException;

}
