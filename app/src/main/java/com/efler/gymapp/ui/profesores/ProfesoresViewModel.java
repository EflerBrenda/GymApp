package com.efler.gymapp.ui.profesores;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfesoresViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Usuario>> mutableProfesores;


    public ProfesoresViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Usuario>> getMutableProfesores() {
        if(mutableProfesores==null){
            mutableProfesores=new MutableLiveData<>();
        }
        return mutableProfesores;
    }

    public void obtenerProfesores() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Usuario>> obtenerProfesoresPromesa = ApiRetrofit.getServiceGym().obtenerProfesores(token);
        obtenerProfesoresPromesa.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> profesores = response.body();
                    mutableProfesores.setValue(profesores);

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