package com.efler.gymapp.ui.alumnos;

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
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Usuario>> mutableAlumnos;


    public AlumnosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Usuario>> getMutableAlumnos() {
        if(mutableAlumnos==null){
            mutableAlumnos=new MutableLiveData<>();
        }
        return mutableAlumnos;
    }

    public void obtenerAlumnos() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Usuario>> obtenerAlumnosPromesa = ApiRetrofit.getServiceGym().obtenerAlumnos(token);
        obtenerAlumnosPromesa.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> alumnos = response.body();
                    mutableAlumnos.setValue(alumnos);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}