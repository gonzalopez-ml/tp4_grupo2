package com.example.pp_grupo2_tp4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.pp_grupo2_tp4.modelos.Articulo;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder> {
    private Context context;
    private List<Articulo> listaArticulos;

    public ArticuloAdapter(Context context, List<Articulo> listaArticulos) {
        this.context = context;
        this.listaArticulos = listaArticulos;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articulo, parent, false);
        return new ArticuloViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo articulo = listaArticulos.get(position);
        holder.txtNombre.setText("Nombre articulo :" + articulo.getNombre());
        holder.txtStock.setText("Stock :" + String.valueOf(articulo.getStock()));
        holder.txtCat.setText("Categoria :" + String.valueOf(articulo.getCategoria().getDescripcion()));
    }

    @Override
    public int getItemCount() {
        return listaArticulos.size();
    }

    public class ArticuloViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtStock;
        TextView txtCat;

        public ArticuloViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtItemNombre);
            txtStock = itemView.findViewById(R.id.txtItemStock);
            txtCat = itemView.findViewById(R.id.txtItemCategoria);
        }
    }
}