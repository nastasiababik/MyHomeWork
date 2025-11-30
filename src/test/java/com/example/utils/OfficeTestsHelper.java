package com.example.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficeTestsHelper {

    /**
     * Метод, который возвращает список id сотрудников
     * @param stm sql-запрос
     * @param departmentId идентификатор отдела из таблицы Department
     * @return список id сотрудников, где элемент списка в формате int
     */
    public static List<Integer> getEmployeeIdsByDepartment(PreparedStatement stm, int departmentId) throws SQLException {
        List<Integer> employeeIds = new ArrayList<>();
        stm.setInt(1, departmentId);
        try (ResultSet rs = stm.executeQuery()) {
            while(rs.next()) {
                employeeIds.add(rs.getInt(1));
            }
        }
        return employeeIds;
    }
}
