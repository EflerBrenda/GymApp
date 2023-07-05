package com.efler.gymapp.ui.ejercicios;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaEjercicioAdapter extends FragmentStateAdapter {
    Context context;
    List<Categoria> lista;

    public CategoriaEjercicioAdapter(@NonNull Fragment fragment,List<Categoria> lista) {
        super(fragment);
        this.lista=lista;

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new CategoriaTabFragment();
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

