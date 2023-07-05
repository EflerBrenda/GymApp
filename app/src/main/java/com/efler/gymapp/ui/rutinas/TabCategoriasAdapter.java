package com.efler.gymapp.ui.rutinas;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.ui.ejercicios.CategoriaTabFragment;

import java.util.List;

public class TabCategoriasAdapter extends FragmentStateAdapter {
    Context context;
    List<Categoria> lista;

    public TabCategoriasAdapter(@NonNull Fragment fragment, List<Categoria> lista) {
        super(fragment);
        this.lista=lista;

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new EjerciciosCategoriaRutinaFragment();
        Bundle args = new Bundle();
        args.putString("categorias", lista.get(position).getDescripcion());
        args.putInt("idCategorias", lista.get(position).getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return lista.size();

    }
}