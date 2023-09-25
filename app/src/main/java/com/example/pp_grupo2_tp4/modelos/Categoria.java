package com.example.pp_grupo2_tp4.modelos;

public class Categoria {
    private Integer id;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
