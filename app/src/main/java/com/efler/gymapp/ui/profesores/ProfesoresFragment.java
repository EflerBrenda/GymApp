package com.efler.gymapp.ui.profesores;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.alumnos.AlumnosAdapter;

import java.util.List;

public class ProfesoresFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ProfesoresViewModel vm;
    private ListView lvProfesores;
    private List<Usuario> listaProfesores;
    private SearchView svBuscaProfesores;
    private View view;
    private ProfesorAdapter pad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(ProfesoresViewModel.class);
        view = inflater.inflate(R.layout.fragment_profesores, container, false);

        vm.getMutableProfesores().observe(getViewLifecycleOwner(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> profesores) {
                listaProfesores= profesores ;
                pad = new ProfesorAdapter(getContext(),R.layout.item_profesor,listaProfesores);
                lvProfesores.setAdapter(pad);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        lvProfesores =view.findViewById(R.id.lvProfesores);
        svBuscaProfesores =view.findViewById(R.id.svBuscaProfesores);
        vm.obtenerProfesores();
        svBuscaProfesores.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        pad.filtrarProfesor(s);
        return false;
    }

}