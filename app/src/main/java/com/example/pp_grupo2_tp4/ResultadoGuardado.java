package com.example.pp_grupo2_tp4;

import com.example.pp_grupo2_tp4.modelos.Articulo;

import java.util.List;

public class ResultadoGuardado {
    private List<Articulo> articulos;
    private boolean guardadoExitoso;

    public ResultadoGuardado(List<Articulo> articulos, boolean guardadoExitoso) {
        this.articulos = articulos;
        this.guardadoExitoso = guardadoExitoso;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}
