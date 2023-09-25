package com.example.pp_grupo2_tp4.ui.alta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pp_grupo2_tp4.R;
import com.example.pp_grupo2_tp4.base.DatabaseConexion;
import com.example.pp_grupo2_tp4.dao.CategoriaDao;
import com.example.pp_grupo2_tp4.modelos.Categoria;

import java.sql.SQLException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAlta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAlta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spinnerCategorias;
    private CategoriaDao categoriaDAO;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAlta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAlta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAlta newInstance(String param1, String param2) {
        FragmentAlta fragment = new FragmentAlta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            categoriaDAO = new CategoriaDao(DatabaseConexion.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Categoria> categorias = categoriaDAO.getAllCategorias();

        ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategorias.findViewById(R.id.spinner);

        spinnerCategorias.setAdapter(adapter);


        return inflater.inflate(R.layout.fragment_alta, container, false);
    }
}