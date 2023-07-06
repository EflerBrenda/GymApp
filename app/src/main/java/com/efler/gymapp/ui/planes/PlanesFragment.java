package com.efler.gymapp.ui.planes;

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
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.alumnos.AlumnosAdapter;
import com.efler.gymapp.ui.alumnos.AlumnosViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class PlanesFragment extends Fragment implements SearchView.OnQueryTextListener {

    private PlanesViewModel vm;
    private RecyclerView rvPlanes;
    private List<Plan> listaPlanes;
    private ExtendedFloatingActionButton btNuevoPlan;
    private SearchView svBuscaPlanes;
    private View view;
    private PlanesAdapter pad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(PlanesViewModel.class);
        view = inflater.inflate(R.layout.fragment_planes, container, false);

        vm.getMutablePlanes().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> planes) {
                listaPlanes= planes ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvPlanes.setLayoutManager(gridLayoutManager);
                pad = new PlanesAdapter(listaPlanes,getContext(),getLayoutInflater());
                rvPlanes.setAdapter(pad);
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        rvPlanes =view.findViewById(R.id.rvPlanes);
        btNuevoPlan= view.findViewById(R.id.btNuevoPlan);
        svBuscaPlanes= view.findViewById(R.id.svBuscaPlanes);
        vm.obtenerPlan();
        btNuevoPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_nuevoPlan);
            }
        });
        svBuscaPlanes.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s!= null){
            if(pad != null)
            {pad.buscar(s);}}
        return false;
    }
}