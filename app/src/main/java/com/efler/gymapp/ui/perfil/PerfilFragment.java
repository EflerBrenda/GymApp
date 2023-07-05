package com.efler.gymapp.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.efler.gymapp.R;
import com.efler.gymapp.databinding.FragmentPerfilBinding;
import com.efler.gymapp.modelo.Plan;
import com.efler.gymapp.modelo.Usuario;
import com.efler.gymapp.request.ApiRetrofit;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vmPerfil;
    private EditText etNombre,etApellido,etEmail,etTelefono,etPlanContratado;
    private Button btCambiarPassword,btVerPlanContratado;
    //private Bundle plan;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vmPerfil = new ViewModelProvider(this).get(PerfilViewModel.class);
        view= inflater.inflate(R.layout.fragment_perfil, container, false);
        vmPerfil.getMutableUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                etNombre.setText(usuario.getNombre());
                etApellido.setText(usuario.getApellido());
                etTelefono.setText(usuario.getTelefono());
                etEmail.setText(usuario.getEmail());
                etPlanContratado.setText(usuario.getPlan().getDescripcion());
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        btCambiarPassword= view.findViewById(R.id.btCambiarPassword);
        etNombre = view.findViewById(R.id.etNombre);
        etApellido= view.findViewById(R.id.etApellido);
        etPlanContratado= view.findViewById(R.id.etPlanContratado);
        etTelefono= view.findViewById(R.id.etTelefono);
        etEmail=view.findViewById(R.id.etEmail);
        btCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_cambiarPassword);
            }
        });
        vmPerfil.ObtenerUsuario();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}