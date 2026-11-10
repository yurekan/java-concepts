package com.example.jdbc;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Create table
        dao.createTable();

        // Insert employees
        dao.insertEmployee(
                new Employee(1, "John", 50000)
        );
        dao.insertEmployee(
                new Employee(2, "Jane", 65000)
        );
        dao.insertEmployee(
                new Employee(3, "Alice", 70000)
        );

        // Retrieve all employees
        System.out.println("All Employess list");
        dao.getAllEmployees().forEach(emp -> System.out.println(emp));

        // Retrieve employee by Id
        System.out.println("Employee with Id: 1");
        System.out.println(dao.getEmployeeById(1));

        // Update employee
        System.out.println("Updating employee with Id: 1");
        dao.updateEmployee(
                new Employee(1, "John Updated", 80000)
        );

        System.out.println("After updating employee with Id: 1");
        System.out.println(dao.getEmployeeById(1));

        // Deleting employee
        System.out.println("Deleting employee with Id: 2");
        dao.deleteEmployee(2);
        System.out.println("After deleting employee with Id: 2");
        dao.getAllEmployees().forEach(emp -> System.out.println(emp));
    }
}
