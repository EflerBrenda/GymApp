package com.efler.gymapp.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> mutableUsuario;


    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }

    public MutableLiveData<Usuario> getMutableUsuario() {
        if(mutableUsuario==null){
            mutableUsuario=new MutableLiveData<>();
        }
        return mutableUsuario;
    }

    public void ObtenerUsuario(){
        String token=ApiRetrofit.obtenerToken(context);
        Call<Usuario> obtenerPerfilPromesa = ApiRetrofit.getServiceGym().obtenerPerfil(token);
        obtenerPerfilPromesa.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                mutableUsuario.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });}
}