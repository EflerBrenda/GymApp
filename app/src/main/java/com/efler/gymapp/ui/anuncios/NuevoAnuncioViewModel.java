package com.efler.gymapp.ui.anuncios;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoAnuncioViewModel extends AndroidViewModel {

    private Context context;

    public NuevoAnuncioViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void crearAnuncio( Anuncio anuncio){

        if(anuncio.getDescripcion().equals("")){
            Toast.makeText(context, "Debe ingresar una descripcion.", Toast.LENGTH_SHORT).show();
            return;
        }
        String token = ApiRetrofit.obtenerToken(context);
        Call<Anuncio> crearAnuncioPromesa = ApiRetrofit.getServiceGym().nuevoAnuncio(token,anuncio);
        crearAnuncioPromesa.enqueue(new Callback<Anuncio>() {
            @Override
            public void onResponse(Call<Anuncio> call, Response<Anuncio> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se agrego el anuncio con exito.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Anuncio> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}