package com.efler.gymapp.ui.pago;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Pago;
import com.efler.gymapp.modelo.PagoUrl;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mutableURL;
    private MutableLiveData<List<Pago>> mutablePagos;

    public PagoViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }

    public MutableLiveData<String> getMutableURL() {
        if(mutableURL==null){
            mutableURL=new MutableLiveData<>();
        }
        return mutableURL;
    }

    public MutableLiveData<List<Pago>> getMutablePagos() {
        if(mutablePagos==null){
            mutablePagos=new MutableLiveData<>();
        }
        return mutablePagos;
    }

    public void obtenerLinkPago(){
        String token= ApiRetrofit.obtenerToken(context);
        Call<PagoUrl> obtenerPromesa = ApiRetrofit.getServiceGym().GenerarLinkPago(token);
        obtenerPromesa.enqueue(new Callback<PagoUrl>() {
            @Override
            public void onResponse(Call<PagoUrl> call, Response<PagoUrl> response) {
                if (response.isSuccessful()) {
                    mutableURL.postValue(response.body().getUrl());
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PagoUrl> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void obtenerPagos(){
        String token= ApiRetrofit.obtenerToken(context);
        Call<List<Pago>> obtenerPromesa = ApiRetrofit.getServiceGym().obtenerPagos(token);
        obtenerPromesa.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    mutablePagos.postValue(response.body());
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}