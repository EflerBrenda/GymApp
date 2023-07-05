package com.efler.gymapp.ui.alumnos;

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
import android.widget.TextView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.anuncios.DetalleAnuncioFragment;
import com.efler.gymapp.ui.anuncios.DetalleAnuncioViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalleAlumnoFragment extends Fragment {

    private DetalleAlumnoViewModel vmDetalle;
    private EditText etNombreAlumnoEdit,etApellidoAlumnoEdit,etTelefonoAlumnoEdit,etEmailAlumnoEdit;
    private FloatingActionButton fabEditAlumno,fabBorrarAlumno;
    private ExtendedFloatingActionButton fabRutinaAlumno;
    private Spinner spPlanAlumnoEdit;
    private View view;
    private String textoBoton;
    private Plan plan;

    public static DetalleAlumnoFragment newInstance() {
        return new DetalleAlumnoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(DetalleAlumnoViewModel.class);
        view =inflater.inflate(R.layout.fragment_detalle_alumno, container, false);
        vmDetalle.getMutablePlan().observe(getViewLifecycleOwner(), new Observer<Bundle>() {
            @Override
            public void onChanged(Bundle bundle) {
                plan= (Plan) bundle.getSerializable("plan");
            }
        });
        vmDetalle.getMutableAlumno().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario alumno) {
                etNombreAlumnoEdit.setText(alumno.getNombre());
                etApellidoAlumnoEdit.setText(alumno.getApellido());
                etTelefonoAlumnoEdit.setText(alumno.getTelefono());
                etEmailAlumnoEdit.setText(alumno.getEmail());

            }
        });
        vmDetalle.getMutableEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                etNombreAlumnoEdit.setEnabled(aBoolean);
                etApellidoAlumnoEdit.setEnabled(aBoolean);
                etTelefonoAlumnoEdit.setEnabled(aBoolean);
                etEmailAlumnoEdit.setEnabled(aBoolean);
                spPlanAlumnoEdit.setEnabled(aBoolean);


            }
        });
        vmDetalle.getMutableTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("GUARDAR")){
                    fabBorrarAlumno.setVisibility(view.INVISIBLE);
                    fabRutinaAlumno.setVisibility(view.INVISIBLE);
                    fabEditAlumno.setImageResource(android.R.drawable.ic_menu_save);
                }
                if(s.equals("EDITAR")){
                    fabBorrarAlumno.setVisibility(view.VISIBLE);
                    fabRutinaAlumno.setVisibility(view.VISIBLE);
                    fabEditAlumno.setImageResource(android.R.drawable.ic_menu_edit);
                    textoBoton= "EDITAR";
                }
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        textoBoton= "EDITAR";
        etNombreAlumnoEdit= view.findViewById(R.id.etNombreAlumnoEdit);
        etApellidoAlumnoEdit= view.findViewById(R.id.etApellidoAlumnoEdit);
        etTelefonoAlumnoEdit= view.findViewById(R.id.etTelefonoAlumnoEdit);
        etEmailAlumnoEdit= view.findViewById(R.id.etEmailAlumnoEdit);
        spPlanAlumnoEdit= view.findViewById(R.id.spPlanAlumnoEdit);
        spPlanAlumnoEdit.setEnabled(false);
        spPlanAlumnoEdit.setClickable(false);
        fabRutinaAlumno= view.findViewById(R.id.fabRutinaAlumno);
        fabEditAlumno= view.findViewById(R.id.fabEditAlumno);
        fabBorrarAlumno= view.findViewById(R.id.fabBorrarAlumno);

        Bundle bundle = getArguments();
        vmDetalle.obtenerAlumno(bundle);
        Usuario alumno= (Usuario) bundle.getSerializable("alumno");
        vmDetalle.cargarSpinerplan(spPlanAlumnoEdit,view,alumno.getPlan().getDescripcion());

        fabEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre= etNombreAlumnoEdit.getText().toString();
                String apellido= etApellidoAlumnoEdit.getText().toString();
                String email= etEmailAlumnoEdit.getText().toString();
                String telefono=etTelefonoAlumnoEdit.getText().toString();
                Integer planId= plan.getId();

                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setEmail(email);
                alumno.setTelefono(telefono);
                alumno.setPlanId(planId);

                vmDetalle.actualizarAlumno(textoBoton,alumno);
                textoBoton= "GUARDAR";
            }
        });
        fabBorrarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Eliminar anuncio")
                        .setMessage("¿Seguro desea eliminar el alumno?")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                vmDetalle.eliminarAlumno(alumno.getId());
                                Navigation.findNavController(view).navigate(R.id.nav_alumnos);
                            }
                        })

                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                Navigation.findNavController(view).navigate(R.id.nav_detalleAlumno);
                            }
                        }).show();
            }
        });
    }

}