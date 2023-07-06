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
import com.efler.gymapp.modelo.Ejercicio_Rutina;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleRutinaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Integer> mutableDia;
    private MutableLiveData<Categoria> mutableCategorias;
    private MutableLiveData<List<Ejercicio_Rutina>> mutableEjercicio;


    public DetalleRutinaViewModel(@NonNull Application application) {
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

    public MutableLiveData<List<Ejercicio_Rutina>> getMutableEjercicio() {
        if(mutableEjercicio==null){
            mutableEjercicio=new MutableLiveData<>();
        }
        return mutableEjercicio;
    }

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
    public void cargarSpinerCategoria(Spinner spinner, View view, Integer dia, int id){
        String token = ApiRetrofit.obtenerToken(view.getContext());
        Call<List<Categoria>> obtenerCategoriaPromesa = ApiRetrofit.getServiceGym().ObtenerCategoriasDiasRutina(token,dia,id);
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
        Call<List<Ejercicio_Rutina>> obtenerEjerciciosCategoriaPromesa = ApiRetrofit.getServiceGym().EjerciciosRutinaPorCategorias(token,idCategoria);
        obtenerEjerciciosCategoriaPromesa.enqueue(new Callback<List<Ejercicio_Rutina>>() {
            @Override
            public void onResponse(Call<List<Ejercicio_Rutina>> call, Response<List<Ejercicio_Rutina>> response) {
                if (response.isSuccessful()) {
                    List<Ejercicio_Rutina>ejercicio_rutinas=response.body();
                    mutableEjercicio.setValue(ejercicio_rutinas);

                } else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ejercicio_Rutina>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void eliminarRutina(Integer id){
        String token=ApiRetrofit.obtenerToken(context);
        Call <Rutina> eliminarPromesa =ApiRetrofit.getServiceGym().bajaRutina(token,id);
        eliminarPromesa.enqueue(new Callback<Rutina>() {
            @Override
            public void onResponse(Call<Rutina> call, Response<Rutina> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se eliminó correctamente.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }}


            @Override
            public void onFailure(Call<Rutina> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public int obtenerPosicion(Spinner sp,String valor){
        for(int i=0;i < sp.getCount();i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(valor)){
                return i;
            }
        }
        return 0;
    }

}