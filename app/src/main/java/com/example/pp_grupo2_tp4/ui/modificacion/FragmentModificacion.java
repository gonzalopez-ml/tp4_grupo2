package com.example.pp_grupo2_tp4.ui.modificacion;

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

import com.example.pp_grupo2_tp4.OnArticuloBuscar;
import com.example.pp_grupo2_tp4.OnArticuloEditar;
import com.example.pp_grupo2_tp4.OnCategoriesDownloadedListener;
import com.example.pp_grupo2_tp4.R;
import com.example.pp_grupo2_tp4.dao.ArticuloBuscarDao;
import com.example.pp_grupo2_tp4.dao.ArticuloEditarDao;
import com.example.pp_grupo2_tp4.dao.CategoriaDao;
import com.example.pp_grupo2_tp4.modelos.Articulo;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentModificacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentModificacion extends Fragment implements OnArticuloBuscar, OnCategoriesDownloadedListener, OnArticuloEditar {

    private Integer idModificar;
    private EditText editTextId;
    private EditText editNombreId;
    private EditText editStock;
    private EditText editCategoria;
    private Spinner spinnerEditarCategorias;
    private ArticuloBuscarDao articuloBuscarDao;
    private ArticuloEditarDao articuloEditarDao;
    private List<Categoria> categoriasDescargadas;
    private CategoriaDao categoriaDAO;


    public FragmentModificacion() {
        // Required empty public constructor
    }

    public static FragmentModificacion newInstance() {
        return new FragmentModificacion();
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentModificacion newInstance(String param1, String param2) {
        FragmentModificacion fragment = new FragmentModificacion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modificacion, container, false);



        editNombreId = rootView.findViewById(R.id.editTextNombreProducto);
        editStock = rootView.findViewById(R.id.editTextStock);
        spinnerEditarCategorias = rootView.findViewById(R.id.spinnerEdit);

        Button btnAceptar = rootView.findViewById(R.id.btnBuscar);
        Button btnEditar = rootView.findViewById(R.id.btnModificar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerEditarCategorias = rootView.findViewById(R.id.spinnerEdit);


                categoriaDAO = new CategoriaDao(FragmentModificacion.this::onCategoriesDownloaded);
                categoriaDAO.execute();

                editTextId = rootView.findViewById(R.id.editTextId);

                if (editTextId.getText().toString() == "") {
                    Toast.makeText(requireContext(), "Por favor, ingrese el ID para buscar el artículo", Toast.LENGTH_SHORT).show();
                }

                idModificar = Integer.parseInt(editTextId.getText().toString());

                articuloBuscarDao = new ArticuloBuscarDao(idModificar, FragmentModificacion.this::onArticuloBuscar);


                articuloBuscarDao.execute();

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String nuevoNombre = editNombreId.getText().toString();
                Integer nuevoStock = Integer.parseInt(editStock.getText().toString());
                String nuevaCategoria = spinnerEditarCategorias.getSelectedItem().toString();

                if (nuevoNombre.toString().isEmpty() || nuevoStock < 0) {
                    Toast.makeText(requireContext(), "Por favor, completa correctamente todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Articulo articuloEditar = new Articulo();
                Categoria categoria = obtenerCategoriaPorDescripcion(nuevaCategoria);

                articuloEditar.setId(idModificar);
                articuloEditar.setNombre(nuevoNombre);
                articuloEditar.setStock(nuevoStock);
                articuloEditar.setCategoria(categoria);

                articuloEditarDao = new ArticuloEditarDao(articuloEditar, FragmentModificacion.this::onArticuloEditar);
                articuloEditarDao.execute();
            }
        });

        return rootView;
    }

    @Override
    public void onArticuloBuscar(Articulo articulo) {
        if (articulo != null) {
            EditText editTextNombreProducto = editNombreId.findViewById(R.id.editTextNombreProducto);
            EditText editTextStock = editStock.findViewById(R.id.editTextStock);
            Spinner spinnerEdit = spinnerEditarCategorias.findViewById(R.id.spinnerEdit);

            editTextNombreProducto.setText(articulo.getNombre());
            editTextStock.setText(String.valueOf(articulo.getStock()));

            int categoriaPosition = -1;
            for (int i = 0; i < categoriasDescargadas.size(); i++) {
                if (categoriasDescargadas.get(i).getId() == articulo.getCategoria().getId()) {
                    categoriaPosition = i;
                    break;
                }
            }

            if (categoriaPosition != -1) {
                spinnerEdit.setSelection(categoriaPosition);
            }
        } else {
            Toast.makeText(requireContext(), "No se encontró el artículo con el ID proporcionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCategoriesDownloaded(List<Categoria> categorias) {
        categoriasDescargadas = categorias;

        List<String> descripcionesCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            descripcionesCategorias.add(categoria.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, descripcionesCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditarCategorias.setAdapter(adapter);
    }

    @Override
    public void onArticuloEditar(Boolean exito) {
        if (exito) {
            Toast.makeText(requireContext(), "Articulo modificado con exito", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(requireContext(), "Por favor, ingrese el ID para buscar el artículo", Toast.LENGTH_SHORT).show();
    }


    private Categoria obtenerCategoriaPorDescripcion(String descripcion) {
        for (Categoria categoria : categoriasDescargadas) {
            if (categoria.getDescripcion().equals(descripcion)) {
                return categoria;
            }
        }
        return null;
    }
}