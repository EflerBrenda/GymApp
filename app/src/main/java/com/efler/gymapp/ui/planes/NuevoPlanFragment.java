package com.efler.gymapp.ui.planes;

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

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.alumnos.NuevoAlumnoFragment;
import com.efler.gymapp.ui.alumnos.NuevoAlumnoViewModel;

public class NuevoPlanFragment extends Fragment {

    private NuevoPlanViewModel mViewModel;
    private EditText etDescripcionNuevoPlan,etPrecioPlan,etDiasPlan;
    private Button btAgregarPlan;
    private View view;

    public static NuevoPlanFragment newInstance() {
        return new NuevoPlanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(NuevoPlanViewModel.class);
        view = inflater.inflate(R.layout.fragment_nuevo_plan, container, false);

        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view) {
        etDescripcionNuevoPlan = view.findViewById(R.id.etDescripcionNuevoPlan);
        etPrecioPlan = view.findViewById(R.id.etPrecioPlan);
        etDiasPlan= view.findViewById(R.id.etDiasPlan);
        btAgregarPlan = view.findViewById(R.id.btAgregarPlan);

        btAgregarPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descripcion= etDescripcionNuevoPlan.getText().toString();
                String precio= etPrecioPlan.getText().toString();
                String dias= etDiasPlan.getText().toString();

                Plan plan= new Plan();
                plan.setDescripcion(descripcion);

                mViewModel.crearPlan(plan,dias,precio);
                Navigation.findNavController(view).navigate(R.id.nav_planes);
            }
        });
    }

}