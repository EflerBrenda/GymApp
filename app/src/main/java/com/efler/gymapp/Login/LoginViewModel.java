package com.efler.gymapp.Login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.efler.gymapp.*;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mutableError;
    private MutableLiveData<Usuario> mutablePerfil;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Integer> getmutableError() {
        if (mutableError == null) { mutableError = new MutableLiveData<>(); }
        return mutableError;
    }
    public MutableLiveData<Usuario> getMutablePerfil() {
        if (mutablePerfil == null) { mutablePerfil = new MutableLiveData<>(); }
        return mutablePerfil;
    }
    public void login(String email, String pass) {
        Usuario usuario= new Usuario(email,pass);
        Call<String> tokenPromesa= ApiRetrofit.getServiceGym().login(email,pass);
        tokenPromesa.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    String token= "Bearer "+ response.body();
                    SharedPreferences sp= context.getSharedPreferences("token",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("token",token);
                    editor.commit();
                    mutableError.setValue(View.INVISIBLE);
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                }
                else{
                    //Log.d("salida","Sin respuesta");
                    mutableError.setValue(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void restablecerPassword(String email){
        Call<Usuario> reestablecerPasswordPromesa = ApiRetrofit.getServiceGym().reestablecerPassword(email);
        reestablecerPasswordPromesa.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se envio email para reestablecer la contraseña.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
