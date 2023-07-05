package com.efler.gymapp.ui.alumnos;

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
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleAlumnoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Usuario> mutableAlumno;
    private MutableLiveData<String> mutableTextoBoton;
    private MutableLiveData<Boolean> mutableEnabled;
    private MutableLiveData<Bundle> mutablePlan;



    public DetalleAlumnoViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }

    public MutableLiveData<Usuario> getMutableAlumno() {
        if(mutableAlumno == null) {
            mutableAlumno = new MutableLiveData<>();
        }
        return mutableAlumno;
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

    public MutableLiveData<Bundle> getMutablePlan() {
        if(mutablePlan == null) {
            mutablePlan = new MutableLiveData<>();
        }
        return mutablePlan;
    }

    public void obtenerAlumno(Bundle bundle){
        mutableAlumno.setValue((Usuario) bundle.getSerializable("alumno"));
    }

    public void actualizarAlumno(String boton, Usuario alumno){

        if(boton.equals("GUARDAR"))
        {
            if(alumno.getNombre().equals("")){
                Toast.makeText(context, "Debe ingresar un nombre.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(alumno.getApellido().equals("")){
                Toast.makeText(context, "Debe ingresar un apellido.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(alumno.getEmail().equals("")){
                Toast.makeText(context, "Debe ingresar un email.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(alumno.getTelefono().equals("")){
                Toast.makeText(context, "Debe ingresar un telefono.", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                String token= ApiRetrofit.obtenerToken(context);
                Call<Usuario> actualizarAlumnoPromesa =ApiRetrofit.getServiceGym().editarAlumno(token,alumno);
                actualizarAlumnoPromesa.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful()){

                            mutableEnabled.postValue(false);
                            mutableTextoBoton.postValue("EDITAR");
                        }
                        else{
                            Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                        }}

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
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

    public void eliminarAlumno(Integer id){
        String token=ApiRetrofit.obtenerToken(context);
        Call <Usuario> eliminarAlumnoPromesa =ApiRetrofit.getServiceGym().bajaAlumno(token,id);
        eliminarAlumnoPromesa.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se eliminó correctamente.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }}


            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void cargarSpinerplan(Spinner spinner, View view, String descripcion){
        String token = ApiRetrofit.obtenerToken(view.getContext());
        Call<List<Plan>> obtenerPlanesPromesa = ApiRetrofit.getServiceGym().obtenerPlanes(token);
        obtenerPlanesPromesa.enqueue(new Callback<List<Plan>>() {
            @Override
            public void onResponse(Call<List<Plan>> call, Response<List<Plan>> response) {
                if(response.isSuccessful()){
                    List<Plan> planesResponse= response.body();
                    List<String> descripcionPlan=new ArrayList<String>();

                    for(int i=0; i<planesResponse.size();i++){
                        descripcionPlan.add(planesResponse.get(i).getDescripcion());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionPlan);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);
                    spinner.setSelection(obtenerPosicion(spinner,descripcion));
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Plan plan= new Plan(planesResponse.get(position).getId(),planesResponse.get(position).getDescripcion(),planesResponse.get(position).getPrecio(),planesResponse.get(position).getDias_mes());
                            Bundle planBundle = new Bundle();
                            planBundle.putSerializable("plan",plan);
                            mutablePlan.postValue(planBundle);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });}
            }

            @Override
            public void onFailure(Call<List<Plan>> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();

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