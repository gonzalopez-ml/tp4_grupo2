package com.example.pp_grupo2_tp4.dao;

import android.os.AsyncTask;

import com.example.pp_grupo2_tp4.modelos.Articulo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ArticuloDao extends AsyncTask<Void, Void, Boolean> {

    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    private Articulo articulo;
    private OnArticuloGuardadoListener listener;

    public ArticuloDao(Articulo articulo, OnArticuloGuardadoListener listener) {
        this.articulo = articulo;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String sql = "INSERT INTO articulo (id, nombre, stock, idCategoria) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, articulo.getId());
            preparedStatement.setString(2, articulo.getNombre());
            preparedStatement.setInt(3, articulo.getStock());
            preparedStatement.setInt(4, articulo.getCategoria().getId());

            int filas = preparedStatement.executeUpdate();

            connection.close();

            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (listener != null) {
            listener.onArticuloGuardado(success);
        }
    }

    public interface OnArticuloGuardadoListener {
        void onArticuloGuardado(boolean exito);
    }
}