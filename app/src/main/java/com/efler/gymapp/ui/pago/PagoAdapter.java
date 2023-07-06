package com.efler.gymapp.ui.pago;

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
import com.efler.gymapp.modelo.Pago;

import java.util.List;

public class PagoAdapter extends ArrayAdapter<Pago> {
    private List<Pago> lista;
    private Context contexto;
    private LayoutInflater layoutInflater;

    public PagoAdapter(@NonNull Context context, int resource, @NonNull List<Pago> objects) {
        super(context, resource, objects);
        lista= objects;
        contexto= context;
        layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){

            itemView=layoutInflater.inflate(R.layout.item_pago,parent,false);

        }
        Pago pago = lista.get(position);
        TextView tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
        tvFechaPago.setText("Fecha: "+pago.getFecha_Pago()+" ,Nro.Transacción: "+pago.getNro_Transaccion());
        return  itemView;
    }
}