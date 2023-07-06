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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.modelo.Rutina_Usuario;
import com.efler.gymapp.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsignarRutinaAlumnoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Rutina>> mutableRutinas;
    private MutableLiveData<Rutina> mutableRutina;


    public AsignarRutinaAlumnoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Rutina> getMutableRutina() {
        if (mutableRutina == null) {
            mutableRutina = new MutableLiveData<>();
        }
        return mutableRutina;
    }

    public MutableLiveData<List<Rutina>> getMutableRutinas() {
        if (mutableRutinas == null) {
            mutableRutinas = new MutableLiveData<>();
        }
        return mutableRutinas;
    }

    public void cargarSpinerRutinas(Spinner spinner, View view) {
        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Rutina>> obtenerPromesa = ApiRetrofit.getServiceGym().obtenerRutinas(token);
        obtenerPromesa.enqueue(new Callback<List<Rutina>>() {
            @Override
            public void onResponse(Call<List<Rutina>> call, Response<List<Rutina>> response) {
                if(response.isSuccessful()){
                    List<Rutina> rutinas = response.body();
                    List<String> descripcionTipo=new ArrayList<String>();

                    for(int i=0; i<rutinas.size();i++){
                        descripcionTipo.add(rutinas.get(i).getDescripcion() +"--Dias: "+rutinas.get(i).getCant_dias());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionTipo);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            Rutina rutina= new Rutina(rutinas.get(position).getId(),rutinas.get(position).getDescripcion(),rutinas.get(position).getCant_dias());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("rut",rutina);
                            mutableRutina.postValue(rutina);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else{
                    Toast.makeText(view.getContext(), "Sin respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rutina>> call, Throwable t) {
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void asignarRutina(Rutina_Usuario rutina){
        String token = ApiRetrofit.obtenerToken(context);
        Call<Rutina_Usuario> obtenerPromesa = ApiRetrofit.getServiceGym().asignarRutina(token,rutina);
        obtenerPromesa.enqueue(new Callback<Rutina_Usuario>() {
            @Override
            public void onResponse(Call<Rutina_Usuario> call, Response<Rutina_Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se asignó la rutina correctamente", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Sin respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Rutina_Usuario> call, Throwable t) {
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}