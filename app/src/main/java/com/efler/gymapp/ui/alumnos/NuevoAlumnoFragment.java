package com.efler.gymapp.ui.alumnos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;
import com.efler.gymapp.ui.anuncios.NuevoAnuncioFragment;
import com.efler.gymapp.ui.anuncios.NuevoAnuncioViewModel;

public class NuevoAlumnoFragment extends Fragment {

    private NuevoAlumnoViewModel mViewModel;
    private EditText etNombreAlumno,etApellidoAlumno,etTelefonoAlumno,etEmailAlumno;
    private Button btAgregarAlumno;
    private Spinner spPlanAlumno;
    private View view;
    private Integer planid;

    public static NuevoAlumnoFragment newInstance() {
        return new NuevoAlumnoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(NuevoAlumnoViewModel.class);
        view = inflater.inflate(R.layout.fragment_nuevo_alumno, container, false);
        mViewModel.getMutablePlan().observe(getViewLifecycleOwner(), new Observer<Plan>() {
            @Override
            public void onChanged(Plan plan) {
                planid= plan.getId();
            }
        });
        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view) {
        etNombreAlumno = view.findViewById(R.id.etNombreAlumno);
        etApellidoAlumno = view.findViewById(R.id.etApellidoAlumno);
        etEmailAlumno= view.findViewById(R.id.etEmailAlumno);
        etTelefonoAlumno = view.findViewById(R.id.etTelefonoAlumno);
        btAgregarAlumno = view.findViewById(R.id.btAgregarAlumno);
        spPlanAlumno = view.findViewById(R.id.spPlanAlumno);
        mViewModel.cargarSpinerplan(spPlanAlumno,view);
        btAgregarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre= etNombreAlumno.getText().toString();
                String apellido= etApellidoAlumno.getText().toString();
                String email= etEmailAlumno.getText().toString();
                String telefono=etTelefonoAlumno.getText().toString();
                Usuario alumno= new Usuario();
                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setEmail(email);
                alumno.setTelefono(telefono);
                alumno.setPlanId(planid);
                mViewModel.crearAlumno(alumno);
                Navigation.findNavController(view).navigate(R.id.nav_alumnos);
            }
        });
    }

}