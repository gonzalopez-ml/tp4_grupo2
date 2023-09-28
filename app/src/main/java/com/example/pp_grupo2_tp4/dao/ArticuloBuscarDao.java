package com.example.pp_grupo2_tp4.dao;

import android.os.AsyncTask;

import com.example.pp_grupo2_tp4.OnArticuloBuscar;
import com.example.pp_grupo2_tp4.modelos.Articulo;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ArticuloBuscarDao extends AsyncTask<Void, Void, Articulo> {

    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    private Integer idArticulo;
    private OnArticuloBuscar listener;


    public ArticuloBuscarDao(Integer idArticulo, OnArticuloBuscar listener) {
        this.idArticulo = idArticulo;
        this.listener = listener;
    }

    public ArticuloBuscarDao(OnArticuloBuscar listener) {
        this.listener = listener;
    }


    @Override
    protected Articulo doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT articulo.id, articulo.nombre, articulo.stock, articulo.idCategoria, categoria.descripcion FROM articulo JOIN categoria ON articulo.idCategoria = categoria.id WHERE articulo.id =" + idArticulo);

            if (resultSet.next()) {
                Articulo articuloLeido = new Articulo();
                articuloLeido.setId(resultSet.getInt("id"));
                articuloLeido.setNombre(resultSet.getString("nombre"));
                articuloLeido.setStock(resultSet.getInt("stock"));
                articuloLeido.setCategoria(new Categoria(resultSet.getInt("idCategoria"), resultSet.getString("descripcion")));

                connection.close();
                return articuloLeido;
            } else {
                connection.close();
                return new Articulo();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Articulo();
        }

    }

    @Override
    protected void onPostExecute(Articulo articulo) {
        if (articulo != null) {
            listener.onArticuloBuscar(articulo);
        }
    }
}
