package com.efler.gymapp.ui.anuncios;

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
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

public class AnunciosAdapter extends RecyclerView.Adapter<AnunciosAdapter.ViewHolder>
{
    private List<Anuncio> lista ;
    private Context context;
    private LayoutInflater layoutInflater;

    public AnunciosAdapter(List<Anuncio> lista, Context context, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_anuncio,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anuncio anuncio = lista.get(position);
        holder.tvDescripcionAnuncio.setText(anuncio.getDescripcion());
        holder.tvProfesorAnuncio.setText("Profe:"+ anuncio.getProfesor().getNombre()+" "+anuncio.getProfesor().getApellido()+"");
        holder.tvFechaAnuncio.setText(anuncio.getFecha_anuncio().toString());
        if(ApiRetrofit.obtenerRolActual(context)!=3){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("anuncio", anuncio );
                    Navigation.findNavController(v).navigate(R.id.nav_detalleAnuncio, bundle);

                }
            });
    }}

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDescripcionAnuncio,tvProfesorAnuncio,tvFechaAnuncio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcionAnuncio = itemView.findViewById(R.id.tvDescripcionAnuncio);
            tvProfesorAnuncio = itemView.findViewById(R.id.tvProfesorAnuncio);
            tvFechaAnuncio = itemView.findViewById(R.id.tvFechaAnuncio);
        }
    }
}
