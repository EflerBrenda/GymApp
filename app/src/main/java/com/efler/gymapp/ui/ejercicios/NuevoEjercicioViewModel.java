package com.efler.gymapp.ui.ejercicios;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoEjercicioViewModel extends AndroidViewModel {

    private Context context;

    public NuevoEjercicioViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void crearEjercicio(Ejercicio ejercicio, View view){

        if(ejercicio.getDescripcion().equals("")){
            Toast.makeText(context, "Debe ingresar una descripcion.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(ejercicio.getExplicacion().equals("")){
            Toast.makeText(context, "Debe ingresar una explicacion.", Toast.LENGTH_SHORT).show();
            return;
        }
        String token = ApiRetrofit.obtenerToken(context);
        Call<Ejercicio> crearEjercicioPromesa = ApiRetrofit.getServiceGym().nuevoEjercicio(token,ejercicio);
        crearEjercicioPromesa.enqueue(new Callback<Ejercicio>() {
            @Override
            public void onResponse(Call<Ejercicio> call, Response<Ejercicio> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se agrego el ejercicio con exito.", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.nav_ejercicios);
                } else {
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ejercicio> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}