package com.efler.gymapp.ui.planes;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.alumnos.DetalleAlumnoFragment;
import com.efler.gymapp.ui.alumnos.DetalleAlumnoViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetallePlanFragment extends Fragment {

    private DetallePlanViewModel vmDetalle;
    private EditText etDescripcionEditPlan,etPrecioPlanEdit,etDiasEditPlan;
    private FloatingActionButton fabEditPlan,fabBorrarPlan;
    private View view;
    private String textoBoton;

    public static DetallePlanFragment newInstance() {
        return new DetallePlanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(DetallePlanViewModel.class);
        view =inflater.inflate(R.layout.fragment_detalle_plan, container, false);
        vmDetalle.getMutablePlan().observe(getViewLifecycleOwner(), new Observer<Plan>() {
            @Override
            public void onChanged(Plan plan) {
                etDescripcionEditPlan.setText(plan.getDescripcion());
                etPrecioPlanEdit.setText(plan.getPrecio()+"");
                etDiasEditPlan.setText(plan.getDias_mes()+"");
            }
        });
        vmDetalle.getMutableEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                etDescripcionEditPlan.setEnabled(aBoolean);
                etPrecioPlanEdit.setEnabled(aBoolean);
                etDiasEditPlan.setEnabled(aBoolean);
            }
        });
        vmDetalle.getMutableTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("GUARDAR")){
                    fabBorrarPlan.setVisibility(view.INVISIBLE);
                    fabEditPlan.setImageResource(android.R.drawable.ic_menu_save);
                }
                if(s.equals("EDITAR")){
                    fabBorrarPlan.setVisibility(view.VISIBLE);
                    fabEditPlan.setImageResource(android.R.drawable.ic_menu_edit);
                    textoBoton= "EDITAR";
                }
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        textoBoton= "EDITAR";
        etDescripcionEditPlan= view.findViewById(R.id.etDescripcionEditPlan);
        etPrecioPlanEdit= view.findViewById(R.id.etPrecioPlanEdit);
        etDiasEditPlan= view.findViewById(R.id.etDiasEditPlan);
        fabBorrarPlan= view.findViewById(R.id.fabBorrarPlan);
        fabEditPlan= view.findViewById(R.id.fabEditPlan);

        Bundle bundle = getArguments();
        vmDetalle.obtenerPlan(bundle);
        Plan plan= (Plan) bundle.getSerializable("planLista");

        fabEditPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String descripcion= etDescripcionEditPlan.getText().toString();
                String precio= etPrecioPlanEdit.getText().toString();
                String dias= etDiasEditPlan.getText().toString();

                plan.setDescripcion(descripcion);

                vmDetalle.actualizarPlan(textoBoton,plan,dias,precio);
                textoBoton= "GUARDAR";
            }
        });
        fabBorrarPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Eliminar anuncio")
                        .setMessage("¿Seguro desea eliminar el plan?")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                vmDetalle.eliminarPlan(plan.getId());
                                Navigation.findNavController(view).navigate(R.id.nav_planes);
                            }
                        })

                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                Navigation.findNavController(view).navigate(R.id.nav_detallePlan);
                            }
                        }).show();
            }
        });
    }

}