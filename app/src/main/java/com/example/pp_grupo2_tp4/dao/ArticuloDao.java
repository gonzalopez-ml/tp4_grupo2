package com.example.pp_grupo2_tp4.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pp_grupo2_tp4.ArticuloAdapter;
import com.example.pp_grupo2_tp4.modelos.Articulo;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ArticuloDao extends AsyncTask<Void, Void, Boolean> {

    private static final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10648785";
    private static final String USER = "sql10648785";
    private static final String PASSWORD = "dc4NMAjMfy";

    private Articulo articulo;
    private ArrayList<Articulo> articuloList = new ArrayList<Articulo>();
    private OnArticuloGuardadoListener listener;

    private RecyclerView lvArticulos;
    private Context context;

    public ArticuloDao(Articulo articulo, OnArticuloGuardadoListener listener) {
        this.articulo = articulo;
        this.listener = listener;
    }

    public ArticuloDao(RecyclerView lv, Context ct){
        this.lvArticulos = lv;
        this.context = ct;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
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

            /*INTENTO GENERAR LA LISTA DE ARTICULOS*/
            /*PREPARO LA QUERY PARA HACER EL SELECT DE ARTICULOS*/
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
        ArticuloAdapter articuloAdapter = new ArticuloAdapter(context, articuloList);
        lvArticulos.setAdapter(articuloAdapter);
    }

    public interface OnArticuloGuardadoListener {
        void onArticuloGuardado(boolean exito);
    }
}