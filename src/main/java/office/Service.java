package office;

import office.dao.DepartmentDAO;
import office.dao.EmployeeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;

    public Service(DepartmentDAO departmentDAO, EmployeeDAO employeeDAO) {
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;

    }

    public void createDB() {
        try {
            employeeDAO.dropTable();
            departmentDAO.dropTable();

            departmentDAO.createTable();
            departmentDAO.insert(new Department(1,"Accounting"));
            departmentDAO.insert(new Department(2,"IT"));
            departmentDAO.insert(new Department(3,"HR"));

            employeeDAO.createTable();
            employeeDAO.insert(new Employee(1, "Pete", 1));
            employeeDAO.insert(new Employee(2, "Ann", 1));
            employeeDAO.insert(new Employee(3, "Liz", 2));
            employeeDAO.insert(new Employee(4, "Tom", 2));
            employeeDAO.insert(new Employee(5, "Todd", 3));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void addDepartment(Department d) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("INSERT INTO Department VALUES(?,?)");
            stm.setInt(1, d.departmentID);
            stm.setString(2, d.getName());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeDepartment(Department d) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("DELETE FROM Department WHERE ID=?");
            stm.setInt(1, d.departmentID);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void addEmployee(Employee empl) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?)");
            stm.setInt(1, empl.getEmployeeId());
            stm.setString(2, empl.getName());
            stm.setInt(3, empl.getDepartmentId());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeEmployee(Employee empl) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("DELETE FROM Employee WHERE ID=?");
            stm.setInt(1, empl.getEmployeeId());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 1. Метод ищет сотрудника по переданному имени и если он один переводит его в HR отдел
    public static void moveToHRByName(String name){
        try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
            PreparedStatement stm = con.prepareStatement("SELECT ID FROM Employee WHERE Name = ?");
            stm.setString(1,name);
            ResultSet rs = stm.executeQuery();

            List<Integer> ids = new ArrayList<>();
            while(rs.next()){
                ids.add(rs.getInt("ID"));
            }

            if (ids.isEmpty()) {
                System.out.println("Не удалось найти сторудника с именем " + name);
            } else if (ids.size() > 1) {
                System.out.println("Найдено несколько сотрудников с именем " +name);
            } else {
                int emplId = ids.get(0);
                PreparedStatement updStm = con.prepareStatement("UPDATE Employee SET DepartmentId = ? WHERE ID = ?");
                updStm.setInt(1, 3);
                updStm.setInt(2, emplId);

                int updRows = updStm.executeUpdate();

                if (updRows == 1) {
                    System.out.println("Сотрудник переведён в HR");
                } else {
                    System.out.println("Произошла ошибка во время перевода сотрудника в HR");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // 2. Метод исправляет на заглавную первую букву имени и возвращает количество исправленных имен
    public static int fixAndCountEmployeeName() {
        int count = 0;

        try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement stm = con.createStatement();
            ResultSet empList = stm.executeQuery("SELECT ID, Name FROM Employee");
            while(empList.next()) {
                int id = empList.getInt("ID");
                String name = empList.getString("Name");
                if(Character.isLowerCase(name.charAt(0))) {
                    String fixName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    PreparedStatement updStm = con.prepareStatement("UPDATE Employee SET Name = ? WHERE ID = ?");
                    updStm.setString(1, fixName);
                    updStm.setInt(2, id);
                    updStm.executeUpdate();

                    count++;
                }
            }

        } catch (Exception e){
            System.out.println(e);
        }

        return count;
    }

    //3. Вывод на экран количества сторудников отдела по названию отдела
    public static void countEmployeeDepartmentByDepartmentName(String departmentName) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
            PreparedStatement stm = con.prepareStatement("""
                                 SELECT COUNT(*) AS EmployeeCount 
                                 FROM Employee 
                                 JOIN Department ON Employee.DepartmentID = Department.ID 
                                 WHERE Department.Name = ?
                                 """
            );
            stm.setString(1, departmentName);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int employeeCount = rs.getInt("EmployeeCount");
                System.out.println(employeeCount > 0 ? employeeCount : "В отделе нет сотрудников");
            } else  {
                System.out.println("Отдел не сущестуует");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
