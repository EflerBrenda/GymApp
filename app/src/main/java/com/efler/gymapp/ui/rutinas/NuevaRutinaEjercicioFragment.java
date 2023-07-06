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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class NuevaRutinaEjercicioFragment extends Fragment {

    private NuevaRutinaEjercicioViewModel viewModel;
    private View view;
    private Spinner spDiasRutinaNuevaNueva,spCategoriaNuevaRutina;
    private TextView tvNombreRutina;
    private TabCategoriasAdapter cad;
    //private ViewPager2 vpCategoriaRutinaEjercicio;
    private ListView lvEjerciciosRutina;
    private EjercicioCategoriaRutinaAdapter ead;
    private List<Ejercicio> lista;
    private List<Ejercicio> listaEjerciciosSeleccionados=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel=new ViewModelProvider(this).get(NuevaRutinaEjercicioViewModel.class);
        view=inflater.inflate(R.layout.fragment_nueva_rutina_ejercicio, container, false);

        viewModel.getMutableDia().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer dia) {
            }
        });
        viewModel.getMutableCategoria().observe(getViewLifecycleOwner(), new Observer<Categoria>() {
            @Override
            public void onChanged(Categoria categoria) {
                viewModel.obtenerEjerciciosCategoria(categoria.getId());
            }
        });
        viewModel.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> ejercicios) {
                lista= ejercicios ;
                ead = new EjercicioCategoriaRutinaAdapter(getContext(),R.layout.item_ejercicio_check,lista);
                //listaEjerciciosSeleccionados= ead.ejerciciosCheck;
                lvEjerciciosRutina.setAdapter(ead);
                /*Log.d("size", ejercicios.size()+"");
                if(ejercicios.size() !=0){
                    for(int i=0; i<ejercicios.size();i++){
                        if(ejercicios.get(i).getCheck()){
                            listaEjerciciosSeleccionados.add(ejercicios.get(i));
                        }

                    }
                }*/


            }
        });

                inicializarVista(view);
        return view;
}

    private void inicializarVista(View view){
        spDiasRutinaNuevaNueva= view.findViewById(R.id.spDiasRutinaNuevaNueva);
        tvNombreRutina= view.findViewById(R.id.tvNombreRutina);
        spCategoriaNuevaRutina= view.findViewById(R.id.SpCategoriaNuevaRutina);
        lvEjerciciosRutina= view.findViewById(R.id.lvEjerciciosRutina);

        Bundle bundle= getArguments();
        Rutina rutina=(Rutina) bundle.getSerializable("rutina");
        tvNombreRutina.setText(rutina.getDescripcion());
        viewModel.cargaSpinerDias(spDiasRutinaNuevaNueva,view, rutina.getCant_dias());
        viewModel.cargarSpinerCategoria(spCategoriaNuevaRutina,view);


    }
}