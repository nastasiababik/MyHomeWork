package office;

import office.dao.DepartmentDAO;
import office.dao.EmployeeDAO;

import java.sql.*;
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

    public void addDepartment(Department d) {
        try {
            departmentDAO.insert(d);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeDepartment(Department d) {
        try {
            departmentDAO.delete(d);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addEmployee(Employee empl) {
        try {
            employeeDAO.insert(empl);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeEmployee(Employee empl) {
        try {
            employeeDAO.delete(empl);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 1. Метод ищет сотрудника по переданному имени и если он один переводит его в HR отдел
    public void moveToHRByName(String name){
        try{
            List<Integer> ids = employeeDAO.getIdsByEmployeeName(name);

            if (ids.isEmpty()) {
                System.out.println("Не удалось найти сторудника с именем " + name);
            } else if (ids.size() > 1) {
                System.out.println("Найдено несколько сотрудников с именем " +name);
            } else {
                int emplId = ids.get(0);
                employeeDAO.setDepartmentByEmployeeId(emplId, 3);

                if (departmentDAO.getEmployeeCountByDepartmentName("HR") > 0) {
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
    public int fixAndCountEmployeeName() {
        int count = 0;

        try {
            for(Employee empList : employeeDAO.getAllEmployee()){
                String name = empList.getName();

                if(Character.isLowerCase(name.charAt(0))) {
                    String fixName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    employeeDAO.updateEmployeeNameById(empList.getEmployeeId(), fixName);

                    count++;
                }
            }

        } catch (Exception e){
            System.out.println(e);
        }

        return count;
    }

    //3. Вывод на экран количества сторудников отдела по названию отдела
    public void countEmployeeDepartmentByDepartmentName(String departmentName) {
        try {
            int employeeCount = departmentDAO.getEmployeeCountByDepartmentName(departmentName);

            if (employeeCount > 0) {
                System.out.println(employeeCount);
            } else  {
                System.out.println("В отделе нет сотрудников");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
