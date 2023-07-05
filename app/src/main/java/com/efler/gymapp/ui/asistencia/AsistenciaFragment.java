package com.efler.gymapp.ui.asistencia;

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
import android.widget.ListView;
import android.widget.SearchView;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Asistencia;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.ui.profesores.ProfesorAdapter;
import com.efler.gymapp.ui.profesores.ProfesoresViewModel;

import java.util.List;

public class AsistenciaFragment extends Fragment  {

    private AsistenciaViewModel vm;
    private ListView lvAsistencias;
    private List<Asistencia> lista;
    private Button btAsistir;
    private View view;
    private AsistenciaAdapter aad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(AsistenciaViewModel.class);
        view = inflater.inflate(R.layout.fragment_asistencia, container, false);

        vm.getMutableAsistencia().observe(getViewLifecycleOwner(), new Observer<List<Asistencia>>() {
            @Override
            public void onChanged(List<Asistencia> asistencias) {
                lista= asistencias ;
                aad = new AsistenciaAdapter(getContext(),R.layout.item_asistencia,lista);
                lvAsistencias.setAdapter(aad);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        lvAsistencias =view.findViewById(R.id.lvAsistencia);
        btAsistir =view.findViewById(R.id.btAsistir);
        vm.obtenerAsistencia();
        btAsistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.asistir();
            }
        });

    }

}