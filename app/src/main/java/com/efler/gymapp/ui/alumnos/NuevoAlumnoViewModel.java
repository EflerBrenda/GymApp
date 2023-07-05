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

public class NuevoAlumnoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Plan> mutablePlan;

    public NuevoAlumnoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Plan> getMutablePlan() {
        if(mutablePlan == null) {
            mutablePlan = new MutableLiveData<>();
        }
        return mutablePlan;
    }

    public void crearAlumno(Usuario alumno){

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
        String token = ApiRetrofit.obtenerToken(context);
        Call<Usuario> crearAlumnoPromesa = ApiRetrofit.getServiceGym().nuevoAlumno(token,alumno);
        crearAlumnoPromesa.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se agrego el alumno con exito.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void cargarSpinerplan(Spinner spinner, View view){
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
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Plan plan= new Plan(planesResponse.get(position).getId(),planesResponse.get(position).getDescripcion(),planesResponse.get(position).getPrecio(),planesResponse.get(position).getDias_mes());
                            Bundle planBundle = new Bundle();
                            planBundle.putSerializable("plan",plan);
                            mutablePlan.postValue(plan);
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
}