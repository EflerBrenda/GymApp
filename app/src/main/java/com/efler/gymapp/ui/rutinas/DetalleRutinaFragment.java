package com.efler.gymapp.ui.rutinas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.modelo.Ejercicio_Rutina;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.ui.ejercicios.EjercicioAdapter;
import com.efler.gymapp.ui.mirutina.MiRutinaAdapter;
import com.efler.gymapp.ui.mirutina.MiRutinaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DetalleRutinaFragment extends Fragment {

    private DetalleRutinaViewModel viewModel;
    private View view;
    private Spinner spDiaRutinaDetalle,spCategoriaRutinaDetalle;
    private RecyclerView rvRutinaEjercicioDetalle;
    private List<Ejercicio_Rutina> listaEjercicios;
    private MiRutinaAdapter ead;
    private Integer donde= 1;
    private FloatingActionButton fabBorrarRutina;
    private Bundle bundle;
    private Rutina rutina;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel=new ViewModelProvider(this).get(DetalleRutinaViewModel.class);
        view=inflater.inflate(R.layout.fragment_detalle_rutina, container, false);
        bundle=getArguments();
        rutina= (Rutina)bundle.getSerializable("rutina");
        viewModel.getMutableDia().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer dia) {

                viewModel.cargarSpinerCategoria(spCategoriaRutinaDetalle,view,dia,rutina.getId());
            }
        });
        viewModel.getMutableCategorias().observe(getViewLifecycleOwner(), new Observer<Categoria>() {
            @Override
            public void onChanged(Categoria categoria) {
                viewModel.obtenerEjerciciosCategoria(categoria.getId());
            }
        });
        viewModel.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<List<Ejercicio_Rutina>>() {
            @Override
            public void onChanged(List<Ejercicio_Rutina> ejercicios) {
                listaEjercicios= ejercicios ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvRutinaEjercicioDetalle.setLayoutManager(gridLayoutManager);
                ead = new MiRutinaAdapter(listaEjercicios,getContext(),getLayoutInflater(),donde);
                rvRutinaEjercicioDetalle.setAdapter(ead);
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        spDiaRutinaDetalle= view.findViewById(R.id.spDiaRutinaDetalle);
        spCategoriaRutinaDetalle= view.findViewById(R.id.spCategoriaRutinaDetalle);
        rvRutinaEjercicioDetalle= view.findViewById(R.id.rvRutinaEjercicioDetalle);
        fabBorrarRutina= view.findViewById(R.id.fabBorrarRutina);
        viewModel.cargaSpinerDias(spDiaRutinaDetalle,view, rutina.getCant_dias());
        fabBorrarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Eliminar Rutina")
                        .setMessage("¿Seguro desea eliminar la rutina?")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                viewModel.eliminarRutina(rutina.getId());
                                Navigation.findNavController(view).navigate(R.id.nav_rutinas);
                            }
                        })

                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                Navigation.findNavController(view).navigate(R.id.nav_detalleRutina,bundle);
                            }
                        }).show();
            }
        });
    }
}