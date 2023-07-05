package com.efler.gymapp.ui.planes;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePlanViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Plan> mutablePlan;
    private MutableLiveData<String> mutableTextoBoton;
    private MutableLiveData<Boolean> mutableEnabled;

    public DetallePlanViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }

    public MutableLiveData<Plan> getMutablePlan() {
        if(mutablePlan == null) {
            mutablePlan = new MutableLiveData<>();
        }
        return mutablePlan;
    }

    public MutableLiveData<String> getMutableTextoBoton() {
        if(mutableTextoBoton == null) {
            mutableTextoBoton = new MutableLiveData<>();
        }
        return mutableTextoBoton;
    }

    public MutableLiveData<Boolean> getMutableEnabled() {
        if(mutableEnabled == null) {
            mutableEnabled = new MutableLiveData<>();
        }
        return mutableEnabled;
    }

    public void obtenerPlan(Bundle bundle){
        mutablePlan.setValue((Plan) bundle.getSerializable("planLista"));
    }

    public void actualizarPlan(String boton, Plan plan, String dias,String precio){
        Integer d=0;
        Double p=0.1;
        if(boton.equals("GUARDAR"))
        {
            if(plan.getDescripcion().equals("")){
                Toast.makeText(context, "Debe ingresar una descripcion.", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                d=Integer.parseInt(dias);
            }
            catch(NumberFormatException i){
                Toast.makeText(context, "Ingrese el campo dias.", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                p=Double.parseDouble(precio);
            }
            catch(NumberFormatException i){
                Toast.makeText(context, "Ingrese el campo precio.", Toast.LENGTH_SHORT).show();
                return;
            }
            plan.setDias_mes(d);
            plan.setPrecio(p);
            String token= ApiRetrofit.obtenerToken(context);
            Call<Plan> actualizarPlanPromesa =ApiRetrofit.getServiceGym().editarPlan(token,plan);
            actualizarPlanPromesa.enqueue(new Callback<Plan>() {
                @Override
                public void onResponse(Call<Plan> call, Response<Plan> response) {
                    if(response.isSuccessful()){
                        mutableEnabled.postValue(false);
                        mutableTextoBoton.postValue("EDITAR");
                    }
                    else{
                        Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                    }}

                @Override
                public void onFailure(Call<Plan> call, Throwable t) {
                    Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            mutableEnabled.setValue(true);
            mutableTextoBoton.setValue("GUARDAR");
        }
    }

    public void eliminarPlan(Integer id){
        String token=ApiRetrofit.obtenerToken(context);
        Call <Plan> eliminarPlanPromesa =ApiRetrofit.getServiceGym().bajaPlan(token,id);
        eliminarPlanPromesa.enqueue(new Callback<Plan>() {
            @Override
            public void onResponse(Call<Plan> call, Response<Plan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se eliminó correctamente.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }}


            @Override
            public void onFailure(Call<Plan> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}