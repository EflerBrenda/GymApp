package com.efler.gymapp.ui.anuncios;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Anuncio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

public class AnunciosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Anuncio>> mutableAnuncios;
    private MutableLiveData<Integer> mutableRolUsuario;


    public AnunciosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Anuncio>> getMutableAnuncios() {
        if(mutableAnuncios==null){
            mutableAnuncios=new MutableLiveData<>();
        }
        return mutableAnuncios;
    }

    public MutableLiveData<Integer> getMutableRolUsuario() {
        if(mutableRolUsuario==null){
            mutableRolUsuario=new MutableLiveData<>();
        }
        return mutableRolUsuario;
    }

    public void obtenerAnuncios() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Anuncio>> obtenerAnunciosPromesa = ApiRetrofit.getServiceGym().obtenerAnuncios(token);
        obtenerAnunciosPromesa.enqueue(new Callback<List<Anuncio>>() {
            @Override
            public void onResponse(Call<List<Anuncio>> call, Response<List<Anuncio>> response) {
                if (response.isSuccessful()) {
                    List<Anuncio> anuncios = response.body();
                    mutableAnuncios.setValue(anuncios);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Anuncio>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void obtenerRol(){

        String token=ApiRetrofit.obtenerToken(context);
        Call<Usuario> obtenerPerfilPromesa = ApiRetrofit.getServiceGym().obtenerPerfil(token);
        obtenerPerfilPromesa.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Usuario perfil = response.body();
                    SharedPreferences sp= getApplication().getSharedPreferences("UsuarioActual",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("rolid",response.body().getRolId());
                    editor.putString("nombreApellido",response.body().getNombre()+" "+response.body().getApellido());
                    editor.commit();
                    mutableRolUsuario.postValue(perfil.getRolId());
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