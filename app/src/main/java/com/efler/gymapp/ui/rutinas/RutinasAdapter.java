package com.efler.gymapp.ui.rutinas;

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
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.ejercicios.EjercicioAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RutinasAdapter extends RecyclerView.Adapter<RutinasAdapter.ViewHolder>
{
    private List<Rutina> lista ;
    private List<Rutina> listaBusqueda;
    private Context context;
    private LayoutInflater layoutInflater;


    public RutinasAdapter(List<Rutina> lista, Context context, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
        listaBusqueda= new ArrayList<>();
        listaBusqueda.addAll(lista);

    }

    public void buscar(String texto){
        int longitud = texto.length();
        if(longitud ==0){
            lista.clear();
            lista.addAll(listaBusqueda);
        }
        else{
            List<Rutina> coleccion= lista.stream()
                    .filter(i -> i.getDescripcion().toLowerCase().contains(texto.toLowerCase()))
                    .collect(Collectors.toList());
            lista.clear();
            lista.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RutinasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_rutina,parent,false);
        return new RutinasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinasAdapter.ViewHolder holder, int position ) {
        Rutina rutina = lista.get(position);
        holder.tvDescripcionRutina.setText("Descripción: "+ rutina.getDescripcion());
        holder.tvDiasRutina.setText("Dias de rutina: "+rutina.getCant_dias()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("ejercicio", ejercicio );
                Navigation.findNavController(view).navigate(R.id.nav_detalleEjercicio, bundle);*/

            }
        });
    }
    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDescripcionRutina,tvDiasRutina;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcionRutina = itemView.findViewById(R.id.tvDescripcionRutina);
            tvDiasRutina = itemView.findViewById(R.id.tvDiasRutina);
        }
    }
}