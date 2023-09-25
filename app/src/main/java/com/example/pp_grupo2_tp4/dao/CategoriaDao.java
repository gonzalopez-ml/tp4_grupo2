package com.example.pp_grupo2_tp4.dao;

import android.os.AsyncTask;

import com.example.pp_grupo2_tp4.OnCategoriesDownloadedListener;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao extends AsyncTask<Void, Void, List<Categoria>> {

    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    private OnCategoriesDownloadedListener listener;

    public CategoriaDao(OnCategoriesDownloadedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Categoria> doInBackground(Void... voids) {
        List<Categoria> categorias = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, descripcion FROM categoria");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descripcion = resultSet.getString("descripcion");

                Categoria categoria = new Categoria(id, descripcion);
                categorias.add(categoria);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorias;
    }

    @Override
    protected void onPostExecute(List<Categoria> categorias) {
        if (listener != null) {
            listener.onCategoriesDownloaded(categorias);
        }
    }
}
