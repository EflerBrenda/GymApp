package com.efler.gymapp.ui.alumnos;

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
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.anuncios.AnunciosAdapter;
import com.efler.gymapp.ui.anuncios.AnunciosViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class AlumnosFragment  extends Fragment implements SearchView.OnQueryTextListener {

    private AlumnosViewModel vm;
    private RecyclerView rvAlumnos;
    private List<Usuario> listaAlumnos;
    private ExtendedFloatingActionButton btNuevoAlumno;
    private SearchView svBuscaAlumnos;
    private View view;
    private AlumnosAdapter aad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(AlumnosViewModel.class);
        view = inflater.inflate(R.layout.fragment_alumnos, container, false);

        vm.getMutableAlumnos().observe(getViewLifecycleOwner(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> alumnos) {
                listaAlumnos= alumnos ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvAlumnos.setLayoutManager(gridLayoutManager);
                aad = new AlumnosAdapter(listaAlumnos,getContext(),getLayoutInflater());
                rvAlumnos.setAdapter(aad);
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        rvAlumnos =view.findViewById(R.id.rvAlumnos);
        btNuevoAlumno= view.findViewById(R.id.btNuevoAlumno);
        svBuscaAlumnos= view.findViewById(R.id.svBuscaAlumnos);
        vm.obtenerAlumnos();
        btNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_nuevoAlumno);
            }
        });
        svBuscaAlumnos.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        aad.filtrarAlumno(s);
        return false;
    }
}