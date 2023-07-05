package com.efler.gymapp.ui.ejercicios;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;
import com.efler.gymapp.ui.anuncios.AnunciosAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class CategoriaTabFragment extends Fragment {
    private RecyclerView rvEjercicios;
    private EjercicioAdapter ejercicioAdapter;
    private CategoriaTabViewModel viewModel;
    private View view;
    private List<Ejercicio> listaEjercicios;
    private ExtendedFloatingActionButton btNuevoEjercicio;
    private Integer donde= 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(CategoriaTabViewModel.class);
        view= inflater.inflate(R.layout.fragment_categoria_tab, container, false);

        viewModel.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> ejercicios) {
                listaEjercicios= ejercicios ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvEjercicios.setLayoutManager(gridLayoutManager);
                EjercicioAdapter ead = new EjercicioAdapter(listaEjercicios,getContext(),getLayoutInflater(),donde);
                rvEjercicios.setAdapter(ead);
            }
        });
        inicializarVista(view);
        return view;
    }

   public void inicializarVista(View view){
        rvEjercicios= view.findViewById(R.id.rvEjercicios);
       Bundle args = getArguments();
       Integer idCategoria= args.getInt("idCategorias");
        if(ApiRetrofit.obtenerRolActual(getContext()) !=3){
            btNuevoEjercicio= view.findViewById(R.id.btNuevoEjercicio);
            btNuevoEjercicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.nav_nuevoEjercicio,args);
                }
            });
        }
        viewModel.obtenerEjerciciosCategoria(idCategoria);
   }
}

