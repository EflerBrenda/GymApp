package com.efler.gymapp.ui.rutinas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.ui.ejercicios.CategoriaEjercicioAdapter;
import com.efler.gymapp.ui.ejercicios.EjercicioAdapter;
import com.efler.gymapp.ui.mirutina.MiRutinaViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class NuevaRutinaEjercicioFragment extends Fragment {

    private NuevaRutinaEjercicioViewModel viewModel;
    private View view;
    private Spinner spDiasRutinaNuevaNueva;
    private TextView tvNombreRutina;
    private TabCategoriasAdapter cad;
    private ViewPager2 vpCategoriaRutinaEjercicio;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel=new ViewModelProvider(this).get(NuevaRutinaEjercicioViewModel.class);
        view=inflater.inflate(R.layout.fragment_nueva_rutina_ejercicio, container, false);

        viewModel.getMutableDia().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer dia) {
                //viewModel.cargarSpinerCategoria(spCategoriaEjercicioRutina,view,dia);
            }
        });
        viewModel.getMutableCategorias().observe(getViewLifecycleOwner(), new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> categorias) {
                cad = new TabCategoriasAdapter(getParentFragment(),categorias);
                vpCategoriaRutinaEjercicio.setAdapter(cad);
                TabLayout tabLayout = view.findViewById(R.id.tlCategoriaRutinaEjercicio);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                new TabLayoutMediator(tabLayout, vpCategoriaRutinaEjercicio,
                        (tab, position) -> tab.setText(categorias.get(position).getDescripcion())
                ).attach();
            }

        });
        /*viewModel.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> ejercicios) {
                listaEjercicios= ejercicios ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvRutinaEjercicio.setLayoutManager(gridLayoutManager);
                ead = new EjercicioAdapter(listaEjercicios,getContext(),getLayoutInflater(),donde);
                rvRutinaEjercicio.setAdapter(ead);
            }
        });*/

        inicializarVista(view);
        return view;
}

    private void inicializarVista(View view){
        spDiasRutinaNuevaNueva= view.findViewById(R.id.spDiasRutinaNuevaNueva);
        tvNombreRutina= view.findViewById(R.id.tvNombreRutina);
        vpCategoriaRutinaEjercicio= view.findViewById(R.id.vpCategoriaRutinaEjercicio);

        /*spCategoriaEjercicioRutina= view.findViewById(R.id.spCategoriaEjercicioRutina);
        rvRutinaEjercicio= view.findViewById(R.id.rvRutinaEjercicio);
        //spCategoriaEjercicioRutina.setEnabled(false);*/
        Bundle bundle= getArguments();
        Rutina rutina=(Rutina) bundle.getSerializable("rutina");
        tvNombreRutina.setText(rutina.getDescripcion());
        viewModel.cargaSpinerDias(spDiasRutinaNuevaNueva,view, rutina.getCant_dias());
        viewModel.obtenerCategorias();
    }
}