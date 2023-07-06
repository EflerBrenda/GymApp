package com.efler.gymapp.ui.rutinas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Ejercicio;

import java.util.List;

public class EjerciciosCategoriaRutinaFragment extends Fragment {

    private EjerciciosCategoriaRutinaViewModel viewModel;
    private ListView lvEjerciciosRutina;
    private EjercicioCategoriaRutinaAdapter ead;
    private List<Ejercicio> lista;
    private List<Ejercicio> listaAGuardar;
    private View view;
    private CheckedTextView ctvDescripcionEjercicio;

    public static EjerciciosCategoriaRutinaFragment newInstance() {
        return new EjerciciosCategoriaRutinaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(EjerciciosCategoriaRutinaViewModel.class);
        view= inflater.inflate(R.layout.fragment_ejercicios_categoria_rutina, container, false);
        viewModel.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> ejercicios) {
                lista= ejercicios ;
                ead = new EjercicioCategoriaRutinaAdapter(getContext(),R.layout.item_ejercicio_check,lista);
                lvEjerciciosRutina.setAdapter(ead);


            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view) {
        lvEjerciciosRutina= view.findViewById(R.id.lvEjerciciosRutinaqqq);
        ctvDescripcionEjercicio = view.findViewById(R.id.ctvDescripcionEjercicio);
        lvEjerciciosRutina.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        lvEjerciciosRutina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getContext(), "Checkeado", Toast.LENGTH_SHORT).show();
            }

        });
        Bundle bundle= getArguments();
        Integer categoriaId= bundle.getInt("idCategorias");
        viewModel.obtenerEjerciciosCategoria(categoriaId);
    }

}