package com.example.pp_grupo2_tp4.ui.alta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pp_grupo2_tp4.OnCategoriesDownloadedListener;
import com.example.pp_grupo2_tp4.R;
import com.example.pp_grupo2_tp4.dao.ArticuloDao;
import com.example.pp_grupo2_tp4.dao.CategoriaDao;
import com.example.pp_grupo2_tp4.modelos.Articulo;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAlta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAlta extends Fragment implements OnCategoriesDownloadedListener {


    private Spinner spinnerCategorias;
    private CategoriaDao categoriaDAO;
    private ArticuloDao articuloDao;
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextStock;
    private List<Categoria> categoriasDescargadas;

    public FragmentAlta() {
    }

    public static FragmentAlta newInstance() {
        return new FragmentAlta();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alta, container, false);

        spinnerCategorias = rootView.findViewById(R.id.spinnerEdit);

        categoriaDAO = new CategoriaDao(this);
        categoriaDAO.execute();

        Button btnAceptar = rootView.findViewById(R.id.btnAceptar);
        editTextId = rootView.findViewById(R.id.editTextId);
        editTextNombre = rootView.findViewById(R.id.editTextNombreProducto);
        editTextStock = rootView.findViewById(R.id.editTextStock);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarArticulo();
            }
        });

        return rootView;
    }

    private void guardarArticulo() {
        String descripcionCategoria = spinnerCategorias.getSelectedItem().toString();
        Categoria categoriaSeleccionada = obtenerCategoriaPorDescripcion(descripcionCategoria);
        Integer stock = Integer.parseInt(editTextStock.getText().toString());

        if (editTextId.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() ||
                editTextStock.getText().toString().isEmpty() || stock < 0) {
            Toast.makeText(requireContext(), "Por favor, completa correctamente todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Articulo articulo = new Articulo();
        articulo.setId(Integer.parseInt(editTextId.getText().toString()));
        articulo.setNombre(editTextNombre.getText().toString());
        articulo.setStock(Integer.parseInt(editTextStock.getText().toString()));
        articulo.setCategoria(categoriaSeleccionada);

        ArticuloDao articuloDao = new ArticuloDao(articulo, new ArticuloDao.OnArticuloGuardadoListener() {
            @Override
            public void onArticuloGuardado(boolean exito) {
                if (exito) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "Artículo guardado exitosamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "Error al guardar el artículo, articulo existente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        articuloDao.execute();
    }

    private Categoria obtenerCategoriaPorDescripcion(String descripcion) {
        for (Categoria categoria : categoriasDescargadas) {
            if (categoria.getDescripcion().equals(descripcion)) {
                return categoria;
            }
        }
        return null;
    }


    @Override
    public void onCategoriesDownloaded(List<Categoria> categorias) {
        categoriasDescargadas = categorias;

        List<String> descripcionesCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            descripcionesCategorias.add(categoria.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, descripcionesCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);
    }
}
