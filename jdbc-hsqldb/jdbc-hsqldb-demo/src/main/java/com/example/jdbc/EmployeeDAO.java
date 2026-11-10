package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public void createTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS employee (
                    id INT PRIMARY KEY,
                    name VARCHAR(100),
                    salary DOUBLE
                )
                """;

        try{
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(sql);

            System.out.println("Employee Table Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertEmployee(Employee e){
        String sql = """
                INSERT INTO employee (id, name, salary)
                VALUES (?, ?, ?)
        """;

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, e.getId());
            ps.setString(2, e.getName());
            ps.setDouble(3, e.getSalary());

            ps.executeUpdate();

            System.out.println("Employee Inserted");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();

        String sql = """
                SELECT * FROM employee;
        """;

        try{
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary")
                );

                employees.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(int id){
        String sql = """
                SELECT * FROM employee WHERE id = ?;
        """;

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateEmployee(Employee e){
        String sql = """
                UPDATE employee SET name = ?, salary = ? WHERE id = ?;
        """;

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, e.getName());
            ps.setDouble(2, e.getSalary());
            ps.setInt(3, e.getId());

            int rows = ps.executeUpdate();

            System.out.println("Employee Updated");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteEmployee(int id){
        String sql = """
                DELETE FROM employee WHERE id = ?;
        """;

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            System.out.println("Employee Deleted");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
