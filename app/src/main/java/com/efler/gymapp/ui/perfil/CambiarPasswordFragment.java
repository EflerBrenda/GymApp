package com.efler.gymapp.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.efler.gymapp.R;

public class CambiarPasswordFragment extends Fragment {

    private CambiarPasswordViewModel mViewModel;
    private Button btCambiarPasswordActual;
    private TextView etPasswordActual,etPasswordNueva,etPasswordNuevaRepetir;


    public static CambiarPasswordFragment newInstance() {
        return new CambiarPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);
        View view = inflater.inflate(R.layout.fragment_cambiar_password, container, false);
        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view) {

        etPasswordActual= view.findViewById(R.id.etEnviarEmail);
        etPasswordNueva= view.findViewById(R.id.etPasswordNueva);
        etPasswordNuevaRepetir= view.findViewById(R.id.etPasswordNuevaRepetir);
        btCambiarPasswordActual= view.findViewById(R.id.btEnviarEmail);
        btCambiarPasswordActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passActual=etPasswordActual.getText().toString();
                String passNueva=etPasswordNueva.getText().toString();
                String passRepetir=etPasswordNuevaRepetir.getText().toString();
                mViewModel.cambiarPassword(passActual,passNueva,passRepetir);
            }
        });
    }

}