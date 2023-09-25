package com.example.pp_grupo2_tp4;

import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.util.List;

public interface OnCategoriesDownloadedListener {
    void onCategoriesDownloaded(List<Categoria> categorias);
}
