package com.efler.gymapp.ui.mirutina;

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
import com.efler.gymapp.modelo.Ejercicio_Rutina;
import com.efler.gymapp.ui.ejercicios.EjercicioAdapter;

import java.util.List;

public class MiRutinaAdapter  extends RecyclerView.Adapter<MiRutinaAdapter.ViewHolder>
{
    private List<Ejercicio_Rutina> lista ;
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer donde;

    public MiRutinaAdapter(List<Ejercicio_Rutina> lista, Context context, LayoutInflater layoutInflater,Integer donde) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.donde=donde;

    }

    @NonNull
    @Override
    public MiRutinaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_ejercicio,parent,false);
        return new MiRutinaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiRutinaAdapter.ViewHolder holder, int position ) {
        Ejercicio_Rutina ejercicio = lista.get(position);
        holder.tvDescripcionEjercicio.setText(ejercicio.getEjercicio().getDescripcion()+" Reps: "+ejercicio.getRepeticiones()+" Cantidad: "+ejercicio.getCantidad());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ejercicio", ejercicio.getEjercicio() );
                if(donde==1){
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