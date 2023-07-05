package com.efler.gymapp.ui.mirutina;

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

import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiRutinaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Integer> mutableDia;
    private MutableLiveData<Categoria> mutableCategorias;
    private MutableLiveData<List<Ejercicio>> mutableEjercicio;


    public MiRutinaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Integer> getMutableDia() {
        if(mutableDia==null){
            mutableDia=new MutableLiveData<>();
        }
        return mutableDia;
    }

    public MutableLiveData<Categoria> getMutableCategorias() {
        if(mutableCategorias==null){
            mutableCategorias=new MutableLiveData<>();
        }
        return mutableCategorias;
    }

    public MutableLiveData<List<Ejercicio>> getMutableEjercicio() {
        if(mutableEjercicio==null){
            mutableEjercicio=new MutableLiveData<>();
        }
        return mutableEjercicio;
    }

    public void cargaSpinerDias(Spinner spinner, View view) {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Integer>> obtenerDiasPromesa = ApiRetrofit.getServiceGym().obtenerCantDiasRutina(token);
        obtenerDiasPromesa.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if(response.isSuccessful()){
                    List<Integer> diasResponse= response.body();
                    List<String> descripcionDias=new ArrayList<String>();

                    for(int i=0; i<diasResponse.size();i++){
                        descripcionDias.add("Día: "+ diasResponse.get(i));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionDias);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Integer dia= diasResponse.get(position);
                            Bundle bundle= new Bundle();
                            bundle.putSerializable("dia",dia);
                            mutableDia.postValue(dia);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
            }}

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void cargarSpinerCategoria(Spinner spinner, View view, Integer dia){
        String token = ApiRetrofit.obtenerToken(view.getContext());
        Call<List<Categoria>> obtenerCategoriaPromesa = ApiRetrofit.getServiceGym().obtenerCategoriasDia(token,dia);
        obtenerCategoriaPromesa.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> categoriasResponse= response.body();
                    List<String> descripcionCategoria=new ArrayList<String>();

                    for(int i=0; i<categoriasResponse.size();i++){
                        descripcionCategoria.add(categoriasResponse.get(i).getDescripcion());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionCategoria);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Categoria cat= new Categoria(categoriasResponse.get(position).getId(),categoriasResponse.get(position).getDescripcion());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("categoria",cat);
                            mutableCategorias.postValue(cat);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });}
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void obtenerEjerciciosCategoria(Integer idCategoria ) {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Ejercicio>> obtenerEjerciciosCategoriaPromesa = ApiRetrofit.getServiceGym().EjerciciosPorCategorias(token,idCategoria);
        obtenerEjerciciosCategoriaPromesa.enqueue(new Callback<List<Ejercicio>>() {
            @Override
            public void onResponse(Call<List<Ejercicio>> call, Response<List<Ejercicio>> response) {
                if (response.isSuccessful()) {
                    List<Ejercicio> ejercicios = response.body();
                    mutableEjercicio.setValue(ejercicios);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ejercicio>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}