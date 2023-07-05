package com.efler.gymapp.ui.ejercicios;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
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

public class DetalleEjercicioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Ejercicio> mutableEjercicio;
    private MutableLiveData<String> mutableTextoBoton;
    private MutableLiveData<Boolean> mutableEnabled;
    private MutableLiveData<Bundle> mutableCategoria;



    public DetalleEjercicioViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }

    public MutableLiveData<Ejercicio> getMutableEjercicio() {
        if(mutableEjercicio == null) {
            mutableEjercicio = new MutableLiveData<>();
        }
        return mutableEjercicio;
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

    public MutableLiveData<Bundle> getMutableCategoria() {
        if(mutableCategoria == null) {
            mutableCategoria = new MutableLiveData<>();
        }
        return mutableCategoria;
    }



    public void obtenerEjercicio(Bundle bundle){
        mutableEjercicio.setValue((Ejercicio) bundle.getSerializable("ejercicio"));
    }

    public void actualizarEjercicio(String boton, Ejercicio e){

        if(boton.equals("GUARDAR"))
        {
            if(e.getDescripcion().equals("")){
                Toast.makeText(context, "Debe ingresar una descripcion.", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(e.getExplicacion().equals("")){
                Toast.makeText(context, "Debe ingresar una explicacion.", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                String token= ApiRetrofit.obtenerToken(context);
                Call<Ejercicio> actualizarEjercicioPromesa =ApiRetrofit.getServiceGym().editarEjercicio(token,e);
                actualizarEjercicioPromesa.enqueue(new Callback<Ejercicio>() {
                    @Override
                    public void onResponse(Call<Ejercicio> call, Response<Ejercicio> response) {
                        if(response.isSuccessful()){

                            mutableEnabled.postValue(false);
                            mutableTextoBoton.postValue("EDITAR");
                        }
                        else{
                            Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                        }}

                    @Override
                    public void onFailure(Call<Ejercicio> call, Throwable t) {
                        Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }
        else{
            mutableEnabled.setValue(true);
            mutableTextoBoton.setValue("GUARDAR");
        }
    }

    public void eliminarEjercicio(Integer id){
        String token=ApiRetrofit.obtenerToken(context);
        Call <Ejercicio> eliminarEjercicioPromesa =ApiRetrofit.getServiceGym().bajaEjercicio(token,id);
        eliminarEjercicioPromesa.enqueue(new Callback<Ejercicio>() {
            @Override
            public void onResponse(Call<Ejercicio> call, Response<Ejercicio> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se eliminó correctamente.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }}


            @Override
            public void onFailure(Call<Ejercicio> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void cargarSpinerCategoria(Spinner spinner, View view,String descripcion){
        String token = ApiRetrofit.obtenerToken(view.getContext());
        Call<List<Categoria>> obtenerCategoriasPromesa = ApiRetrofit.getServiceGym().obtenerCategorias(token);
        obtenerCategoriasPromesa.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> categoriaResponse= response.body();
                    List<String> descripcionTipo=new ArrayList<String>();

                   for(int i=0; i<categoriaResponse.size();i++){
                        descripcionTipo.add(categoriaResponse.get(i).getDescripcion());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionTipo);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);
                    spinner.setSelection(obtenerPosicion(spinner,descripcion));
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Categoria ca= new Categoria(categoriaResponse.get(position).getId(),categoriaResponse.get(position).getDescripcion());
                            Bundle categoria = new Bundle();
                            categoria.putSerializable("cat",ca);
                            mutableCategoria.postValue(categoria);
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

    public void configurarSpiner(Spinner spinner,String descripcion){
        spinner.setSelection(obtenerPosicion(spinner,descripcion));
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