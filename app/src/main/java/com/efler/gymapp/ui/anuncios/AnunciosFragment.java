package com.efler.gymapp.ui.anuncios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efler.gymapp.R;
import com.efler.gymapp.databinding.FragmentAnunciosBinding;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.request.ApiRetrofit;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AnunciosFragment extends Fragment {

    private AnunciosViewModel vm;
    private RecyclerView rvAnuncios;
    private List<Anuncio> listaAnuncios;
    private ExtendedFloatingActionButton btNuevoAnuncio;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(AnunciosViewModel.class);
        view = inflater.inflate(R.layout.fragment_anuncios, container, false);

        vm.getMutableAnuncios().observe(getViewLifecycleOwner(), new Observer<List<Anuncio>>() {
            @Override
            public void onChanged(List<Anuncio> anuncios) {
                listaAnuncios= anuncios ;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvAnuncios.setLayoutManager(gridLayoutManager);
                AnunciosAdapter aad = new AnunciosAdapter(listaAnuncios,getContext(),getLayoutInflater());
                rvAnuncios.setAdapter(aad);
            }
        });
        vm.getMutableRolUsuario().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer != 3){
                    btNuevoAnuncio.setVisibility(view.VISIBLE);
                }
                else{
                    btNuevoAnuncio.setVisibility(view.INVISIBLE);
                }
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        rvAnuncios =view.findViewById(R.id.rvAnuncios);
        btNuevoAnuncio= view.findViewById(R.id.btNuevoAnuncio);
        vm.obtenerAnuncios();
        vm.obtenerRol();
        btNuevoAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_nuevoAnuncio);
            }
        });
    }

}