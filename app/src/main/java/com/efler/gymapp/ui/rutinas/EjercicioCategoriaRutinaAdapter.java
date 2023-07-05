package com.efler.gymapp.ui.rutinas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Asistencia;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.modelo.Rutina;

import java.util.List;

public class EjercicioCategoriaRutinaAdapter extends ArrayAdapter<Ejercicio> {
    private List<Ejercicio> lista;
    private Context contexto;
    private LayoutInflater layoutInflater;

    public EjercicioCategoriaRutinaAdapter(@NonNull Context context, int resource, @NonNull List<Ejercicio> objects) {
        super(context, resource, objects);
        lista= objects;
        contexto= context;
        layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){

            itemView=layoutInflater.inflate(R.layout.item_ejercicio_check,parent,false);

        }
        Ejercicio ejercicio = lista.get(position);
        CheckBox ctvDescripcionEjercicio = itemView.findViewById(R.id.ctvDescripcionEjercicio);
        ctvDescripcionEjercicio.setText(ejercicio.getDescripcion());
        return  itemView;
    }
}