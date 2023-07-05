package com.efler.gymapp.ui.alumnos;

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
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;
import com.efler.gymapp.ui.anuncios.AnunciosAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder>
{
    private List<Usuario> lista ;
    private List<Usuario> listaBusqueda;
    private Context context;
    private LayoutInflater layoutInflater;

    public AlumnosAdapter(List<Usuario> lista, Context context, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
        listaBusqueda= new ArrayList<>();
        listaBusqueda.addAll(lista);

    }
    public void filtrarAlumno(String nombre){
        int longitud = nombre.length();
        if(longitud ==0){
            lista.clear();
            lista.addAll(listaBusqueda);
        }
        else{
            List<Usuario> coleccion= lista.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
            lista.clear();
            lista.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlumnosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_alumno,parent,false);
        return new AlumnosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnosAdapter.ViewHolder holder, int position) {
        Usuario alumno = lista.get(position);
        holder.tvNombreApellido.setText("Alumno: "+alumno.getNombre()+" "+alumno.getApellido());
        holder.tvPlanAlumno.setText("Plan:"+ alumno.getPlan().getDescripcion()+"");

        if(ApiRetrofit.obtenerRolActual(context)!=3){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("alumno", alumno );
                    Navigation.findNavController(v).navigate(R.id.nav_detalleAlumno, bundle);

                }
            });
        }}

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombreApellido,tvPlanAlumno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreApellido = itemView.findViewById(R.id.tvNombreApellido);
            tvPlanAlumno = itemView.findViewById(R.id.tvPlanAlumno);
        }
    }
}
