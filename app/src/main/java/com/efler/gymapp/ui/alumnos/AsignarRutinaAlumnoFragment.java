package com.efler.gymapp.ui.alumnos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Rutina;
import com.efler.gymapp.modelo.Rutina_Usuario;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.ejercicios.DetalleEjercicioViewModel;

public class AsignarRutinaAlumnoFragment extends Fragment {

    private AsignarRutinaAlumnoViewModel mViewModel;
    private Spinner spAsignarRutina;
    private Button btAsignarRutina;
    private View view;


    public static AsignarRutinaAlumnoFragment newInstance() {
        return new AsignarRutinaAlumnoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AsignarRutinaAlumnoViewModel.class);
        view= inflater.inflate(R.layout.fragment_asignar_rutina_alumno, container, false);
        mViewModel.getMutableRutina().observe(getViewLifecycleOwner(), new Observer<Rutina>() {
            @Override
            public void onChanged(Rutina rutina) {
                btAsignarRutina.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle= getArguments();
                        Usuario alumno= (Usuario)  bundle.getSerializable("alumno");
                        Rutina_Usuario ru= new Rutina_Usuario();
                        ru.setAlumnoId(alumno.getId());
                        ru.setRutinaId(rutina.getId());
                        mViewModel.asignarRutina(ru);
                    }
                });
            }
        });
        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view){
        spAsignarRutina= view.findViewById(R.id.spAsignarRutina);
        btAsignarRutina= view.findViewById(R.id.btAsignarRutina);
        mViewModel.cargarSpinerRutinas(spAsignarRutina,view);



    }

}