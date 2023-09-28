package com.example.pp_grupo2_tp4.dao;

import android.os.AsyncTask;

import com.example.pp_grupo2_tp4.OnArticuloBuscar;
import com.example.pp_grupo2_tp4.OnArticuloEditar;
import com.example.pp_grupo2_tp4.ResultadoGuardado;
import com.example.pp_grupo2_tp4.modelos.Articulo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ArticuloEditarDao extends AsyncTask<Void, Void, Boolean> {

    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    private Articulo articuloModificado;
    private OnArticuloEditar listener;

    public ArticuloEditarDao(Articulo articulo, OnArticuloEditar listener) {
        this.articuloModificado = articulo;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String updateQuery = "UPDATE articulo SET nombre = ?, stock = ?, idCategoria = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, articuloModificado.getNombre());
            preparedStatement.setInt(2, articuloModificado.getStock());
            preparedStatement.setInt(3, articuloModificado.getCategoria().getId());
            preparedStatement.setInt(4, articuloModificado.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean exito) {
        listener.onArticuloEditar(exito);
    }
}
