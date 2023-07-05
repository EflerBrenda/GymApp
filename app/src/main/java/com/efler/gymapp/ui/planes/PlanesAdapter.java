package com.efler.gymapp.ui.planes;

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
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlanesAdapter extends RecyclerView.Adapter<PlanesAdapter.ViewHolder>
{
    private List<Plan> lista;
    private List<Plan> listaBusqueda;
    private Context context;
    private LayoutInflater layoutInflater;

    public PlanesAdapter(List<Plan> lista, Context context, LayoutInflater layoutInflater) {
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
            List<Plan> coleccion= lista.stream()
                    .filter(i -> i.getDescripcion().toLowerCase().contains(texto.toLowerCase()))
                    .collect(Collectors.toList());
            lista.clear();
            lista.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_plan,parent,false);
        return new PlanesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanesAdapter.ViewHolder holder, int position) {
        Plan plan = lista.get(position);
        holder.tvDescripcionPlan.setText(plan.getDescripcion());


        if(ApiRetrofit.obtenerRolActual(context)!=3){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("planLista", plan );
                    Navigation.findNavController(v).navigate(R.id.nav_detallePlan, bundle);

                }
            });
        }}

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDescripcionPlan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcionPlan = itemView.findViewById(R.id.tvDescripcionPlan);
        }
    }
}
