package com.efler.gymapp.ui.ejercicios;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EjerciciosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Categoria>> mutableCategoria;


    public EjerciciosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<List<Categoria>> getMutableCategoria() {
        if(mutableCategoria==null){
            mutableCategoria=new MutableLiveData<>();
        }
        return mutableCategoria;
    }
    public void obtenerCategorias() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Categoria>> obtenerCategoriasPromesa = ApiRetrofit.getServiceGym().obtenerCategorias(token);
        obtenerCategoriasPromesa.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    List<Categoria> categorias = response.body();
                    mutableCategoria.setValue(categorias);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}