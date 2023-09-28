package com.example.pp_grupo2_tp4.ui.listado;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pp_grupo2_tp4.ArticuloAdapter;
import com.example.pp_grupo2_tp4.OnListarArticulos;
import com.example.pp_grupo2_tp4.R;
import com.example.pp_grupo2_tp4.dao.ArticuloDao;
import com.example.pp_grupo2_tp4.dao.CategoriaDao;
import com.example.pp_grupo2_tp4.modelos.Articulo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListado extends Fragment implements OnListarArticulos {

    private ArticuloDao articuloDao;

    public FragmentListado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListado.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListado newInstance(String param1, String param2) {
        FragmentListado fragment = new FragmentListado();
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
        View rootView = inflater.inflate(R.layout.fragment_listado, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerArticulos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        articuloDao = new ArticuloDao(this::onListarArticulos);
        articuloDao.execute();
        return rootView;
    }

    @Override
    public void onListarArticulos(List<Articulo> articulos) {
        ArticuloAdapter articuloAdapter = new ArticuloAdapter(requireContext(), articulos);

        // Asigna el adaptador al RecyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerArticulos);
        recyclerView.setAdapter(articuloAdapter);
    }
}