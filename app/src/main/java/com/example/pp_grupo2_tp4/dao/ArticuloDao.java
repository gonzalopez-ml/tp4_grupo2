package com.example.pp_grupo2_tp4.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pp_grupo2_tp4.ArticuloAdapter;
import com.example.pp_grupo2_tp4.OnListarArticulos;
import com.example.pp_grupo2_tp4.ResultadoGuardado;
import com.example.pp_grupo2_tp4.modelos.Articulo;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDao extends AsyncTask<Void, Void, ResultadoGuardado> {

    private boolean paraGuardar = false;

    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    private Articulo articulo;
    private ArrayList<Articulo> articuloList = new ArrayList<Articulo>();
    private OnArticuloGuardadoListener listener;
    private OnListarArticulos listarArticulosListener;

    private RecyclerView lvArticulos;
    private Context context;

    public ArticuloDao(Articulo articulo, OnArticuloGuardadoListener listener) {
        this.articulo = articulo;
        this.listener = listener;
        paraGuardar = true;
    }

    public ArticuloDao(OnListarArticulos listener) {
        this.listarArticulosListener = listener;
    }

    @Override
    protected ResultadoGuardado doInBackground(Void... voids) {
        List<Articulo> articulos = new ArrayList<>();
        boolean guardadoExitoso = false;
        if (!paraGuardar) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT articulo.id, articulo.nombre, articulo.stock, articulo.idCategoria, categoria.descripcion FROM articulo JOIN categoria ON articulo.idCategoria = categoria.id");

                while (resultSet.next()){
                    Articulo articuloLeido = new Articulo();
                    articuloLeido.setId(resultSet.getInt("id"));
                    articuloLeido.setNombre(resultSet.getString("nombre"));
                    articuloLeido.setStock(resultSet.getInt("stock"));
                    articuloLeido.setCategoria(new Categoria(resultSet.getInt("idCategoria"), resultSet.getString("descripcion")));
                    articulos.add(articuloLeido);
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                String sql = "INSERT INTO articulo (id, nombre, stock, idCategoria) VALUES (?, ?, ?, ?)";
                Statement statement = connection.createStatement();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, articulo.getId());
                preparedStatement.setString(2, articulo.getNombre());
                preparedStatement.setInt(3, articulo.getStock());
                preparedStatement.setInt(4, articulo.getCategoria().getId());

                int filas = preparedStatement.executeUpdate();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM articulo");

                Articulo articuloLeido;
                while (resultSet.next()){
                    articuloLeido = new Articulo();
                    articuloLeido.setId(resultSet.getInt("id"));
                    articuloLeido.setNombre(resultSet.getString("nombre"));
                    articuloLeido.setStock(resultSet.getInt("stock"));
                    articuloLeido.setCategoria(new Categoria(resultSet.getInt("idCategoria")));
                    articuloList.add(articuloLeido);
                }


                connection.close();

                guardadoExitoso = filas > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResultadoGuardado(articulos, guardadoExitoso);

    }

    @Override
    protected void onPostExecute(ResultadoGuardado result) {
            if (result != null && !result.getArticulos().isEmpty()) {
                listarArticulosListener.onListarArticulos(result.getArticulos());
            }
            else if (listener != null && result != null && result.isGuardadoExitoso()) {
            listener.onArticuloGuardado(true);
        } else {
                listener.onArticuloGuardado(false);
            }
    }

    public interface OnArticuloGuardadoListener {
        void onArticuloGuardado(boolean exito);
    }
}