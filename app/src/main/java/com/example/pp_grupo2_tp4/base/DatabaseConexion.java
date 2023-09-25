package com.example.pp_grupo2_tp4.base;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConexion {
    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error al cargar el controlador JDBC");
        }
    }
}
