package com.efler.gymapp.ui.rutinas;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RutinasViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Rutina>> mutableRutina;


    public RutinasViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Rutina>> getMutableRutina() {
        if(mutableRutina==null){
            mutableRutina=new MutableLiveData<>();
        }
        return mutableRutina;
    }

    public void obtenerRutinas() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Rutina>> obtenerPromesa = ApiRetrofit.getServiceGym().obtenerRutinas(token);
        obtenerPromesa.enqueue(new Callback<List<Rutina>>() {
            @Override
            public void onResponse(Call<List<Rutina>> call, Response<List<Rutina>> response) {
                if (response.isSuccessful()) {
                    List<Rutina> rutinas = response.body();
                    mutableRutina.setValue(rutinas);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rutina>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}