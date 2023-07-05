package com.efler.gymapp.ui.asistencia;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Asistencia;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsistenciaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Asistencia>> mutableAsistencia;


    public AsistenciaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Asistencia>> getMutableAsistencia() {
        if(mutableAsistencia==null){
            mutableAsistencia=new MutableLiveData<>();
        }
        return mutableAsistencia;
    }

    public void obtenerAsistencia() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Asistencia>> obtenerAsistenciasPromesa = ApiRetrofit.getServiceGym().obtenerAsistencia(token);
        obtenerAsistenciasPromesa.enqueue(new Callback<List<Asistencia>>() {
            @Override
            public void onResponse(Call<List<Asistencia>> call, Response<List<Asistencia>> response) {
                if (response.isSuccessful()) {
                    List<Asistencia> asistencias = response.body();
                    mutableAsistencia.setValue(asistencias);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Asistencia>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void asistir(){
        String token = ApiRetrofit.obtenerToken(context);
        Call<Asistencia> obtenerAsistenciasPromesa = ApiRetrofit.getServiceGym().nuevaAsistencia(token);
        obtenerAsistenciasPromesa.enqueue(new Callback<Asistencia>() {
            @Override
            public void onResponse(Call<Asistencia> call, Response<Asistencia> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "¡Bienvenido/a,"+ response.body().getUsuario().getNombre() +"!", Toast.LENGTH_SHORT).show();
                    obtenerAsistencia();
                } else {
                    Toast.makeText(context, "Lo siento, solo puedes poner presente una vez al día", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Asistencia> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}