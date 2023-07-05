package com.efler.gymapp.ui.asistencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Asistencia;
import com.efler.gymapp.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AsistenciaAdapter extends ArrayAdapter<Asistencia> {
    private List<Asistencia> lista;
    private Context contexto;
    private LayoutInflater layoutInflater;

    public AsistenciaAdapter(@NonNull Context context, int resource, @NonNull List<Asistencia> objects) {
        super(context, resource, objects);
        lista= objects;
        contexto= context;
        layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){

            itemView=layoutInflater.inflate(R.layout.item_asistencia,parent,false);

        }
        Asistencia asistencia = lista.get(position);
        TextView tvFechaAsitencia = itemView.findViewById(R.id.tvFechaAsitencia);
        tvFechaAsitencia.setText("Fecha: "+asistencia.getFecha_asistencia()+" ,Hora:"+asistencia.getHora_asistencia());
        return  itemView;
    }
}