package com.efler.gymapp.ui.mirutina;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.efler.gymapp.modelo.Ejercicio;

public class DetalleEjercicioRutinaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Ejercicio> mutableEjercicio;

    public DetalleEjercicioRutinaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Ejercicio> getMutableEjercicio() {
        if (mutableEjercicio == null) {
            mutableEjercicio = new MutableLiveData<>();
        }
        return mutableEjercicio;
    }

    public void obtenerEjercicio(Bundle bundle){
        mutableEjercicio.setValue((Ejercicio) bundle.getSerializable("ejercicio"));
    }
}