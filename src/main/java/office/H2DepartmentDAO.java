package office;

import office.dao.DepartmentDAO;

import java.sql.*;

public class H2DepartmentDAO implements DepartmentDAO {
    private final H2DbConnector connector;

    public H2DepartmentDAO(H2DbConnector con) {
        this.connector = con;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection con = connector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");
        }
    }

    @Override
    public void dropTable() throws SQLException {
        try (Connection con = connector.getConnection();
        Statement stm = con.createStatement()) {
            stm.executeUpdate("DROP TABLE Department IF EXISTS");
        }
    }

    @Override
    public void insert(Department department) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("INSERT INTO Department(ID, NAME) VALUES (?, ?)")) {
            stm.setInt(1, department.getDepartmentID());
            stm.setString(2, department.getName());
            stm.executeUpdate();
        }
    }

    @Override
    public void delete(Department department) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("DELETE FROM Department WHERE ID = ?")) {
            stm.setInt(1, department.getDepartmentID());
            stm.executeUpdate();
        }
    }

    @Override
    public int getEmployeeCountByDepartmentName(String name) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stm = con.prepareStatement("SELECT COUNT(*) AS EmployeeCount  FROM Employee " +
                     "JOIN Department ON Employee.DepartmentID = Department.ID  WHERE Department.Name = ?")) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("EmployeeCount");
            }
        }
        return 0;
    }

}
