package com.efler.gymapp.ui.planes;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Plan>> mutablePlanes;


    public PlanesViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Plan>> getMutablePlanes() {
        if(mutablePlanes==null){
            mutablePlanes=new MutableLiveData<>();
        }
        return mutablePlanes;
    }

    public void obtenerPlan() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Plan>> obtenerPlanesPromesa = ApiRetrofit.getServiceGym().obtenerPlanes(token);
        obtenerPlanesPromesa.enqueue(new Callback<List<Plan>>() {
            @Override
            public void onResponse(Call<List<Plan>> call, Response<List<Plan>> response) {
                if (response.isSuccessful()) {
                    List<Plan> planes = response.body();
                    mutablePlanes.setValue(planes);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Plan>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}