package com.efler.gymapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<Usuario> mutablePerfil;
    private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Usuario> getMutablePerfil() {
        if (mutablePerfil == null) { mutablePerfil = new MutableLiveData<>(); }
        return mutablePerfil;
    }


    public void obtenerPerfil(){

        String token=ApiRetrofit.obtenerToken(context);
        Call<Usuario> obtenerPerfilPromesa = ApiRetrofit.getServiceGym().obtenerPerfil(token);
        obtenerPerfilPromesa.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Usuario perfil = response.body();
                    SharedPreferences sp= getApplication().getSharedPreferences("UsuarioActual",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("id",response.body().getId());
                    editor.commit();
                    mutablePerfil.postValue(perfil);
                }
                else{
                    Toast.makeText(context, "Ocurrio un error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });}
}
