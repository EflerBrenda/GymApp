package com.efler.gymapp.ui.ejercicios;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaTabViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Ejercicio>> mutableEjercicio;


    public CategoriaTabViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<List<Ejercicio>> getMutableEjercicio() {
        if(mutableEjercicio==null){
            mutableEjercicio=new MutableLiveData<>();
        }
        return mutableEjercicio;
    }

    public void obtenerEjerciciosCategoria(Integer idCategoria ) {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Ejercicio>> obtenerEjerciciosCategoriaPromesa = ApiRetrofit.getServiceGym().EjerciciosPorCategorias(token,idCategoria);
        obtenerEjerciciosCategoriaPromesa.enqueue(new Callback<List<Ejercicio>>() {
            @Override
            public void onResponse(Call<List<Ejercicio>> call, Response<List<Ejercicio>> response) {
                if (response.isSuccessful()) {
                    List<Ejercicio> ejercicios = response.body();
                    mutableEjercicio.setValue(ejercicios);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ejercicio>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}