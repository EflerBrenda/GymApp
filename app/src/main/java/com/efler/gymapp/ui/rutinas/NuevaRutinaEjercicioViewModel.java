package com.efler.gymapp.ui.rutinas;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaRutinaEjercicioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Integer> mutableDia;
    private MutableLiveData<List<Categoria>> mutableCategorias;
    //private MutableLiveData<List<Ejercicio>> mutableEjercicio;


    public NuevaRutinaEjercicioViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Integer> getMutableDia() {
        if(mutableDia==null){
            mutableDia=new MutableLiveData<>();
        }
        return mutableDia;
    }

    public MutableLiveData<List<Categoria>>getMutableCategorias() {
        if(mutableCategorias==null){
            mutableCategorias=new MutableLiveData<>();
        }
        return mutableCategorias;
    }

    /*public MutableLiveData<List<Ejercicio>> getMutableEjercicio() {
        if(mutableEjercicio==null){
            mutableEjercicio=new MutableLiveData<>();
        }
        return mutableEjercicio;
    }*/

    public void cargaSpinerDias(Spinner spinner, View view,Integer dias) {

        List<String> descripcionDias=new ArrayList<String>();
        Integer d=1;
        for(int i=0; i<dias;i++){
            descripcionDias.add("Día: "+ (d++));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionDias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer dia= Integer.parseInt(descripcionDias.get(position).toString().replace("Día: ",""));
                Bundle bundle= new Bundle();
                bundle.putSerializable("dia",dia);
                mutableDia.postValue(dia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void obtenerCategorias() {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Categoria>> obtenerCategoriasPromesa = ApiRetrofit.getServiceGym().obtenerCategorias(token);
        obtenerCategoriasPromesa.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    List<Categoria> categorias = response.body();
                    mutableCategorias.setValue(categorias);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}