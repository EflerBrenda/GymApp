package com.efler.gymapp.ui.mirutina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.modelo.Ejercicio_Rutina;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.modelo.Rutina_Usuario;
import com.efler.gymapp.ui.ejercicios.EjercicioAdapter;

import java.util.List;

public class MiRutinaFragment extends Fragment {

    private MiRutinaViewModel viewModel;
    private View view;
    private Spinner spDiaRutina,spCategoriaEjercicioRutina;
    private RecyclerView rvRutinaEjercicio;
    private List<Ejercicio_Rutina> listaEjercicios;
    private MiRutinaAdapter ead;
    private Integer donde=20;
    private TextView tvNombreMiRutina;
    private Rutina_Usuario rUsuario=new Rutina_Usuario();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel=new ViewModelProvider(this).get(MiRutinaViewModel.class);
        view=inflater.inflate(R.layout.fragment_mi_rutina, container, false);

        viewModel.getMutableDia().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer dia) {
                if(rUsuario.getId()!=0){
                    viewModel.cargarSpinerCategoria(spCategoriaEjercicioRutina,view,dia,rUsuario.getRutina().getId());

                }
            }
        });
        viewModel.getMutableCategorias().observe(getViewLifecycleOwner(), new Observer<Categoria>() {
            @Override
            public void onChanged(Categoria categoria) {
                if(rUsuario.getId()!=0){
                viewModel.obtenerEjerciciosCategoria(categoria.getId(),rUsuario.getRutina().getId());}
            }
        });
        viewModel.getMutableRutina().observe(getViewLifecycleOwner(), new Observer<Rutina_Usuario>() {
            @Override
            public void onChanged(Rutina_Usuario ru) {
                if(ru.getId()!=0){
                    rUsuario= ru;
                    tvNombreMiRutina.setText(ru.getRutina().getDescripcion()+"");
                }
                else{
                    rUsuario= ru;
                    tvNombreMiRutina.setText("El usuario no posee rutina");
                }

            }
        });
        viewModel.getMutableEjercicioRutina().observe(getViewLifecycleOwner(), new Observer<List<Ejercicio_Rutina>>() {
            @Override
            public void onChanged(List<Ejercicio_Rutina> ejerciciosRutina) {
                listaEjercicios= ejerciciosRutina ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvRutinaEjercicio.setLayoutManager(gridLayoutManager);
                ead = new MiRutinaAdapter(listaEjercicios,getContext(),getLayoutInflater(),donde);
                rvRutinaEjercicio.setAdapter(ead);
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        spDiaRutina= view.findViewById(R.id.spDiaRutina);
        spCategoriaEjercicioRutina= view.findViewById(R.id.spCategoriaEjercicioRutina);
        rvRutinaEjercicio= view.findViewById(R.id.rvRutinaEjercicio);
        tvNombreMiRutina= view.findViewById(R.id.tvNombreMiRutina);
        viewModel.obtenerMiRutina();
        //spCategoriaEjercicioRutina.setEnabled(false);
        viewModel.cargaSpinerDias(spDiaRutina,view);
    }
}