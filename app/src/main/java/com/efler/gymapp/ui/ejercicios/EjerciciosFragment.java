package com.efler.gymapp.ui.ejercicios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.efler.gymapp.R;
import com.efler.gymapp.databinding.FragmentEjerciciosBinding;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.ui.anuncios.AnunciosViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class EjerciciosFragment extends Fragment {
    CategoriaEjercicioAdapter categoriaAdapter;
    ViewPager2 viewPager;
    EjerciciosViewModel viewModel;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(EjerciciosViewModel.class);
        view=inflater.inflate(R.layout.fragment_ejercicios, container, false);

        viewModel.getMutableCategoria().observe(getViewLifecycleOwner(), new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> categorias) {
                categoriaAdapter = new CategoriaEjercicioAdapter(getParentFragment(),categorias);
                viewPager.setAdapter(categoriaAdapter);
                TabLayout tabLayout = view.findViewById(R.id.tab_layout);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                new TabLayoutMediator(tabLayout, viewPager,
                        (tab, position) -> tab.setText(categorias.get(position).getDescripcion())
                ).attach();
            }
        });

        inicializarVista(view);
        return view;
    }

    private void inicializarVista(View view){
        viewPager = view.findViewById(R.id.pager);
        viewModel.obtenerCategorias();
    }
}