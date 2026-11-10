package com.example.jdbc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:hsqldb:file:./data/employeedb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try{
            Files.createDirectories(Path.of("./data"));
        } catch(Exception e){
            throw new RuntimeException("Unable to create directory ./data");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
