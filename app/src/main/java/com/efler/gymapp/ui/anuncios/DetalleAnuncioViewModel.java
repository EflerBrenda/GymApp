package com.efler.gymapp.ui.anuncios;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.request.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleAnuncioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Anuncio> mutableAnuncio;
    private MutableLiveData<String> mutableTextoBoton;
    private MutableLiveData<Boolean> mutableEnabled;


    public DetalleAnuncioViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }

    public MutableLiveData<Anuncio> getMutableAnuncio() {
        if(mutableAnuncio == null) {
            mutableAnuncio = new MutableLiveData<>();
        }
        return mutableAnuncio;
    }

    public MutableLiveData<String> getMutableTextoBoton() {
        if(mutableTextoBoton == null) {
            mutableTextoBoton = new MutableLiveData<>();
        }
        return mutableTextoBoton;
    }

    public MutableLiveData<Boolean> getMutableEnabled() {
        if(mutableEnabled == null) {
            mutableEnabled = new MutableLiveData<>();
        }
        return mutableEnabled;
    }

    public void obtenerAnuncio(Bundle bundle){
        mutableAnuncio.setValue((Anuncio) bundle.getSerializable("anuncio"));
    }

    public void actualizarAnuncio(String boton, Anuncio a){

        if(boton.equals("GUARDAR"))
        {
            if(a.getDescripcion().equals("")){
                Toast.makeText(context, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            }
            else{
                String token=ApiRetrofit.obtenerToken(context);
                Call <Anuncio> actualizarAnuncioPromesa =ApiRetrofit.getServiceGym().editarAnuncio(token,a);
                actualizarAnuncioPromesa.enqueue(new Callback<Anuncio>() {
                    @Override
                    public void onResponse(Call<Anuncio> call, Response<Anuncio> response) {
                        if(response.isSuccessful()){

                            mutableEnabled.postValue(false);
                            mutableTextoBoton.postValue("EDITAR");
                        }
                        else{
                            Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                        }}

                    @Override
                    public void onFailure(Call<Anuncio> call, Throwable t) {
                        Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }
        else{
            mutableEnabled.setValue(true);
            mutableTextoBoton.setValue("GUARDAR");
        }
    }

    public void eliminarAnuncio(Integer id){
        String token=ApiRetrofit.obtenerToken(context);
        Call <Anuncio> eliminarAnuncioPromesa =ApiRetrofit.getServiceGym().bajaAnuncio(token,id);
        eliminarAnuncioPromesa.enqueue(new Callback<Anuncio>() {
            @Override
            public void onResponse(Call<Anuncio> call, Response<Anuncio> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se eliminó correctamente.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }}


            @Override
            public void onFailure(Call<Anuncio> call, Throwable t) {
            Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}