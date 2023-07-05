package com.efler.gymapp.ui.anuncios;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.efler.gymapp.Login.LoginActivity;
import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.request.ApiRetrofit;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalleAnuncioFragment extends Fragment {

    private DetalleAnuncioViewModel vmDetalleAnuncio;
    private EditText etDescripcionEditAnuncio;
    private TextView tvProfesorEditAnuncio,tvFechaEditAnuncio;
    private FloatingActionButton fabEditAnuncio,fabBorrarAnuncio;
    private View view;
    private String textoBoton;

    public static DetalleAnuncioFragment newInstance() {
        return new DetalleAnuncioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalleAnuncio = new ViewModelProvider(this).get(DetalleAnuncioViewModel.class);
        view =inflater.inflate(R.layout.fragment_detalle_anuncio, container, false);
        vmDetalleAnuncio.getMutableAnuncio().observe(getViewLifecycleOwner(), new Observer<Anuncio>() {
            @Override
            public void onChanged(Anuncio anuncio) {
                etDescripcionEditAnuncio.setText(anuncio.getDescripcion());
                tvProfesorEditAnuncio.setText(anuncio.getProfesor().getNombre()+" "+anuncio.getProfesor().getApellido()+"");
                tvFechaEditAnuncio.setText(anuncio.getFecha_anuncio());
            }
        });
        vmDetalleAnuncio.getMutableEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                etDescripcionEditAnuncio.setEnabled(aBoolean);
            }
        });
        vmDetalleAnuncio.getMutableTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("GUARDAR")){
                    fabBorrarAnuncio.setVisibility(view.INVISIBLE);
                    fabEditAnuncio.setImageResource(android.R.drawable.ic_menu_save);
                }
                if(s.equals("EDITAR")){
                    fabBorrarAnuncio.setVisibility(view.VISIBLE);
                    fabEditAnuncio.setImageResource(android.R.drawable.ic_menu_edit);
                    textoBoton= "EDITAR";
                }
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        textoBoton= "EDITAR";
        etDescripcionEditAnuncio= view.findViewById(R.id.etDescripcionEditAnuncio);
        tvProfesorEditAnuncio= view.findViewById(R.id.tvProfesorEditAnuncio);
        tvFechaEditAnuncio= view.findViewById(R.id.tvFechaEditAnuncio);
        fabEditAnuncio= view.findViewById(R.id.fabEditAnuncio);
        fabBorrarAnuncio= view.findViewById(R.id.fabBorrarAnuncio);
        Bundle bundle = getArguments();
        vmDetalleAnuncio.obtenerAnuncio(bundle);
        fabEditAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Anuncio an=(Anuncio) bundle.getSerializable("anuncio");
                an.setDescripcion(etDescripcionEditAnuncio.getText().toString());
                vmDetalleAnuncio.actualizarAnuncio(textoBoton,an);
                textoBoton= "GUARDAR";
            }
        });
        fabBorrarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Eliminar anuncio")
                        .setMessage("Seguro desea eliminar el anuncio?")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                Anuncio anuncio=(Anuncio) bundle.getSerializable("anuncio");
                                vmDetalleAnuncio.eliminarAnuncio(anuncio.getId());
                                Navigation.findNavController(view).navigate(R.id.nav_anuncios);
                            }
                        })

                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                Navigation.findNavController(view).navigate(R.id.nav_detalleAnuncio);
                            }
                        }).show();
            }
        });
    }

}