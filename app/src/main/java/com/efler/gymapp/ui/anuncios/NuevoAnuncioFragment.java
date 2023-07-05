package com.efler.gymapp.ui.anuncios;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.request.ApiRetrofit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NuevoAnuncioFragment extends Fragment {

    private NuevoAnuncioViewModel mViewModel;
    private EditText etDescripcionNuevoAnuncio;
    private TextView tvProfesorNuevoAnuncio, tvFechaNuevoAnuncio;
    private Button btAgregarAnuncio;
    private View view;

    public static NuevoAnuncioFragment newInstance() {
        return new NuevoAnuncioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(NuevoAnuncioViewModel.class);
        view = inflater.inflate(R.layout.fragment_nuevo_anuncio, container, false);
        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view) {
        etDescripcionNuevoAnuncio = view.findViewById(R.id.etDescripcionNuevoAnuncio);
        tvProfesorNuevoAnuncio = view.findViewById(R.id.tvProfesorNuevoAnuncio);
        tvProfesorNuevoAnuncio.setText(ApiRetrofit.obtenerNombreUsuarioActual(getContext()));
        tvFechaNuevoAnuncio = view.findViewById(R.id.tvFechaNuevoAnuncio);
        String fecha = obtenerFechaActual();
        tvFechaNuevoAnuncio.setText(fecha);
        btAgregarAnuncio = view.findViewById(R.id.btAgregarAnuncio);
        btAgregarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descripcion = etDescripcionNuevoAnuncio.getText().toString();
                Anuncio anuncio =new Anuncio();
                anuncio.setDescripcion(descripcion);
                anuncio.setProfesorId(ApiRetrofit.obtenerUsuarioActual(getContext()));
                mViewModel.crearAnuncio(anuncio);
                Navigation.findNavController(view).navigate(R.id.nav_anuncios);
            }
        });
    }

    public String obtenerFechaActual() {
        Date fecha = new Date();
        TimeZone zona = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setTimeZone(zona);
        String fechaString = format.format((fecha));
        return fechaString;
    }
}