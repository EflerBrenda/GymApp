package com.efler.gymapp.ui.profesores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfesorAdapter extends ArrayAdapter<Usuario> {
    private List<Usuario> lista;
    private List<Usuario> listaBusqueda;
    private Context contexto;
    private LayoutInflater layoutInflater;

    public ProfesorAdapter(@NonNull Context context, int resource, @NonNull List<Usuario> objects) {
        super(context, resource, objects);
        lista= objects;
        contexto= context;
        layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listaBusqueda= new ArrayList<>();
        listaBusqueda.addAll(lista);
    }
    public void filtrarProfesor(String nombre){
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

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){

            itemView=layoutInflater.inflate(R.layout.item_profesor,parent,false);

        }
        Usuario profesor = lista.get(position);
        TextView tvNombreApellidoProfe = itemView.findViewById(R.id.tvNombreApellidoProfe);
        tvNombreApellidoProfe.setText(profesor.getNombre()+" "+profesor.getApellido());
        return  itemView;
    }
}