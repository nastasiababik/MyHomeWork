package office;

import office.dao.EmployeeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2EmployeeDAO implements EmployeeDAO {
    private final DbConnector connector;

    public H2EmployeeDAO(H2DbConnector con) {
        this.connector = con;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection con = connector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT," +
                    "FOREIGN KEY(DepartmentID) REFERENCES Department(ID) ON DELETE CASCADE)"
            );
        }
    }

    @Override
    public void dropTable() throws SQLException {
        try (Connection con = connector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate("DROP TABLE Employee IF EXISTS");
        }
    }

    @Override
    public void insert(Employee employee) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("INSERT INTO Employee(ID, NAME, DepartmentID) VALUES (?, ?, ?)")) {
            stm.setInt(1, employee.getEmployeeId());
            stm.setString(2, employee.getName());
            stm.setInt(3, employee.getDepartmentId());
            stm.executeUpdate();
        }

    }

    @Override
    public void delete(Employee employee) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("DELETE FROM Employee WHERE ID = ?")) {
            stm.setInt(1, employee.getEmployeeId());
            stm.executeUpdate();

        }
    }

    @Override
    public List<Integer> getIdsByEmployeeName(String name) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (Connection con = connector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT ID FROM Employee WHERE Name = ?")) {
            stm.setString(1,name);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                ids.add(rs.getInt("ID"));
            }
        }
        return ids;
    }

    @Override
    public List<Employee> getAllEmployee() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery("SELECT ID, NAME, DepartmentID FROM Employee ORDER BY ID")) {

            while(rs.next()){
                employees.add(new Employee(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("DepartmentID")));
            }
        }
        return employees;
    }

    @Override
    public void updateEmployeeNameById(int empId, String name) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("UPDATE Employee SET Name = ? WHERE ID = ?")) {
            stm.setString(1, name);
            stm.setInt(2, empId);
            stm.executeUpdate();
        }
    }

    @Override
    public void setDepartmentByEmployeeId(int empId, int deptId) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("UPDATE Employee SET DepartmentId = ? WHERE ID = ?")) {
            stm.setInt(1, deptId);
            stm.setInt(2, empId);
            stm.executeUpdate();
        }
    }
}
