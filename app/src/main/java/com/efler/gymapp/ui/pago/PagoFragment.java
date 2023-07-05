package com.efler.gymapp.ui.pago;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.efler.gymapp.R;
import com.efler.gymapp.ui.perfil.PerfilViewModel;


public class PagoFragment extends Fragment {

    private PagoViewModel mViewModel;
    private Button btPago;
    private View view;

    public static PagoFragment newInstance() {
        return new PagoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        view= inflater.inflate(R.layout.fragment_pago, container, false);
        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view){
        btPago= view.findViewById(R.id.btPagar);
        btPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


}