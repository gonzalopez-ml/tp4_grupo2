package com.example.pp_grupo2_tp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.pp_grupo2_tp4.modelos.Articulo;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ViewHolder> {
    private final Context context;
    private final List<Articulo> listaArticulos;

    public ArticuloAdapter(Context context, List<Articulo> listaArticulos) {
        this.context = context;
        this.listaArticulos = listaArticulos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_listado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articulo articulo = listaArticulos.get(position);
        holder.bind(articulo);
    }

    @Override
    public int getItemCount() {
        return listaArticulos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView idTextView;
        private final TextView nombreTextView;
        private final TextView stockTextView;
        private final TextView categoriaTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.txtViewId);
            nombreTextView = itemView.findViewById(R.id.txtViewNombreProducto);
            stockTextView = itemView.findViewById(R.id.txtViewStock);
            categoriaTextView = itemView.findViewById(R.id.txtViewCategoria);


        }

        public void bind(Articulo articulo) {
            idTextView.setText(articulo.getId());
            nombreTextView.setText(articulo.getNombre());
            stockTextView.setText(articulo.getStock());
            categoriaTextView.setText(articulo.getCategoria().getId());
        }
    }
}
