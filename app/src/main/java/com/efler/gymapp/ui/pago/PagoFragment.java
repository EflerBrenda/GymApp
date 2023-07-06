package com.efler.gymapp.ui.pago;

import androidx.lifecycle.Observer;
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
import android.widget.ListView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Pago;
import com.efler.gymapp.ui.asistencia.AsistenciaAdapter;
import com.efler.gymapp.ui.perfil.PerfilViewModel;

import java.util.List;


public class PagoFragment extends Fragment {

    private PagoViewModel mViewModel;
    private ListView lvPagos;
    private List<Pago> lista;
    private PagoAdapter pad;
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
        mViewModel.getMutableURL().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String url) {
                btPago.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        });
        mViewModel.getMutablePagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                lista= pagos ;
                pad = new PagoAdapter(getContext(),R.layout.item_pago,lista);
                lvPagos.setAdapter(pad);
            }
        });
        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view){
        btPago= view.findViewById(R.id.btPagar);
        lvPagos= view.findViewById(R.id.lvPagos);
        mViewModel.obtenerPagos();
        mViewModel.obtenerLinkPago();
    }


}