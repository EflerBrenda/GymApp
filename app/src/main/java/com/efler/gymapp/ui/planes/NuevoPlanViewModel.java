package com.efler.gymapp.ui.planes;

import android.app.Application;
import android.content.Context;
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

public class NuevoPlanViewModel extends AndroidViewModel {

    private Context context;

    public NuevoPlanViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void crearPlan(Plan plan, String dias, String precio){
        Integer d=0;
        Double p=0.1;
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
        String token = ApiRetrofit.obtenerToken(context);
        Call<Plan> crearPlanPromesa = ApiRetrofit.getServiceGym().nuevoPlan(token,plan);
        crearPlanPromesa.enqueue(new Callback<Plan>() {
            @Override
            public void onResponse(Call<Plan> call, Response<Plan> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se agrego el plan con exito.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Plan> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}