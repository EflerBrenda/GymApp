package com.efler.gymapp.ui.ejercicios;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.ViewHolder>
{
    private List<Ejercicio> lista ;
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer donde;

    public EjercicioAdapter(List<Ejercicio> lista, Context context, LayoutInflater layoutInflater,int donde) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.donde=donde;
    }

    @NonNull
    @Override
    public EjercicioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_ejercicio,parent,false);
        return new EjercicioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioAdapter.ViewHolder holder, int position ) {
        Ejercicio ejercicio = lista.get(position);
        holder.tvDescripcionEjercicio.setText(ejercicio.getDescripcion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ejercicio", ejercicio );
                if(donde == 1){
                    Navigation.findNavController(view).navigate(R.id.nav_detalleEjercicio, bundle);
                }
                if(donde == 2){
                    Navigation.findNavController(view).navigate(R.id.nav_detalleEjercicioRutina, bundle);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDescripcionEjercicio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcionEjercicio = itemView.findViewById(R.id.tvDescripcionEjercicio);

        }
    }
}
