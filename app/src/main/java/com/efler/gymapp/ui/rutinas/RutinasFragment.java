package com.efler.gymapp.ui.rutinas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.SearchView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.alumnos.AlumnosAdapter;
import com.efler.gymapp.ui.alumnos.AlumnosViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class RutinasFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RutinasViewModel vm;
    private RecyclerView rvRutinas;
    private List<Rutina> lista;
    //private ExtendedFloatingActionButton btNuevaRutina;
    private SearchView svBuscaRutinas;
    private View view;
    private RutinasAdapter aad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(RutinasViewModel.class);
        view = inflater.inflate(R.layout.fragment_rutinas, container, false);

        vm.getMutableRutina().observe(getViewLifecycleOwner(), new Observer<List<Rutina>>() {
            @Override
            public void onChanged(List<Rutina> rutinas) {
                lista= rutinas ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvRutinas.setLayoutManager(gridLayoutManager);
                aad = new RutinasAdapter(lista,getContext(),getLayoutInflater());
                rvRutinas.setAdapter(aad);
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        rvRutinas =view.findViewById(R.id.rvRutinas);
        //btNuevaRutina= view.findViewById(R.id.btNuevaRutina);
        svBuscaRutinas= view.findViewById(R.id.svBuscaRutinas);
        vm.obtenerRutinas();
        /*btNuevaRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_nuevaRutina);
            }
        });
        svBuscaRutinas.setOnQueryTextListener(this);*/
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s!= null){
            if(aad != null)
            {
                aad.buscar(s);
            }
        }
        return false;
    }
}