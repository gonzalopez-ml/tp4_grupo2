package com.example.pp_grupo2_tp4;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pp_grupo2_tp4.ui.alta.FragmentAlta;
import com.example.pp_grupo2_tp4.ui.listado.FragmentListado;
import com.example.pp_grupo2_tp4.ui.modificacion.FragmentModificacion;

public class ViewAdapter extends FragmentStateAdapter {
    public ViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentAlta();
            case 1:
                return new FragmentModificacion();
            case 2:
                return new FragmentListado();
            default:
                return new FragmentAlta();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
