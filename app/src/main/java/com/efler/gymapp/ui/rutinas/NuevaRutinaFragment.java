package com.efler.gymapp.ui.rutinas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Rutina;

public class NuevaRutinaFragment extends Fragment {

    private NuevaRutinaViewModel mViewModel;
    private View view;
    private Button btSiquiente;
    private EditText etDescripcionRutinaNueva;
    private Spinner spDiasRutinaNueva;

    public static NuevaRutinaFragment newInstance() {
        return new NuevaRutinaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_nueva_rutina, container, false);
        inicializarVista(view);
        return view;
    }


    public void inicializarVista(View view) {
        etDescripcionRutinaNueva= view.findViewById(R.id.etDescripcionRutinaNueva);
        btSiquiente= view.findViewById(R.id.btSiguiente);
        spDiasRutinaNueva= view.findViewById(R.id.spDiasRutinaNueva);
        String[] arregloDias= {"2","3","5"};
        spDiasRutinaNueva.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,arregloDias));
        btSiquiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rutina rutina= new Rutina();
                if(etDescripcionRutinaNueva.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Debe ingresar una descripción", Toast.LENGTH_SHORT).show();
                    return;
                }
                rutina.setDescripcion(etDescripcionRutinaNueva.getText().toString());
                Integer dias= Integer.parseInt(spDiasRutinaNueva.getSelectedItem().toString());
                rutina.setCant_dias(dias);
                Bundle bundle= new Bundle();
                bundle.putSerializable("rutina",rutina);
                Navigation.findNavController(view).navigate(R.id.nav_nuevaRutinaEjercicioFragment,bundle);
            }
        });

    }

}