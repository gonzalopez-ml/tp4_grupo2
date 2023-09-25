package com.example.pp_grupo2_tp4.dao;

import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    private Connection connection;

    public CategoriaDao(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener todas las categorías
    public List<Categoria> getAllCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        try {
            String sql = "SELECT * FROM categoria";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descripcion = resultSet.getString("descripcion");

                Categoria categoria = new Categoria(id, descripcion);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
}
