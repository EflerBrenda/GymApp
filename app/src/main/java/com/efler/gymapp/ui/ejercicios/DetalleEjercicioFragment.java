package com.efler.gymapp.ui.ejercicios;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Anuncio;
import com.efler.gymapp.modelo.Categoria;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.ui.anuncios.DetalleAnuncioFragment;
import com.efler.gymapp.ui.anuncios.DetalleAnuncioViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DetalleEjercicioFragment extends Fragment {

    private DetalleEjercicioViewModel vmDetalle;
    private EditText etDescripcionEjercicioEdit,etURLExplicacionEdit;
    private FloatingActionButton fabEditEjercicio,fabBorrarEjercicio;
    private Button btVistaPreviaExplicaciónEdit;
    private WebView wbVideoExplicacionEdit;
    private Spinner spCategoria;
    private View view;
    private String textoBoton;
    private Categoria categoria;
    //private Ejercicio ejercicio;

    public static DetalleEjercicioFragment newInstance() {
        return new DetalleEjercicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(DetalleEjercicioViewModel.class);
        view =inflater.inflate(R.layout.fragment_detalle_ejercicio, container, false);
        vmDetalle.getMutableCategoria().observe(getViewLifecycleOwner(), new Observer<Bundle>() {
            @Override
            public void onChanged(Bundle bundle) {
                categoria= (Categoria) bundle.getSerializable("cat");
            }
        });
        vmDetalle.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<Ejercicio>() {
            @Override
            public void onChanged(Ejercicio ejercicio) {
                //ejercicio= ejer;
                etDescripcionEjercicioEdit.setText(ejercicio.getDescripcion());
                etURLExplicacionEdit.setText(ejercicio.getExplicacion());
                String url= comprobarURL(ejercicio.getExplicacion(),getContext());
                wbVideoExplicacionEdit.loadData(url,"text/html","UTF-8");
            }
        });

        vmDetalle.getMutableEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                btVistaPreviaExplicaciónEdit.setEnabled(aBoolean);
                etDescripcionEjercicioEdit.setEnabled(aBoolean);
                etURLExplicacionEdit.setEnabled(aBoolean);
                spCategoria.setEnabled(aBoolean);
                spCategoria.setClickable(aBoolean);
            }
        });
        vmDetalle.getMutableTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("GUARDAR")){
                    fabBorrarEjercicio.setVisibility(view.INVISIBLE);
                    fabEditEjercicio.setImageResource(android.R.drawable.ic_menu_save);
                }
                if(s.equals("EDITAR")){
                    fabBorrarEjercicio.setVisibility(view.VISIBLE);
                    fabEditEjercicio.setImageResource(android.R.drawable.ic_menu_edit);
                    textoBoton= "EDITAR";
                }
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        textoBoton= "EDITAR";
        wbVideoExplicacionEdit= view.findViewById(R.id.wbVideoExplicacionEdit);
        wbVideoExplicacionEdit.setWebViewClient(new WebViewClient());
        WebSettings settings = wbVideoExplicacionEdit.getSettings();
        settings.setJavaScriptEnabled(true);
        btVistaPreviaExplicaciónEdit= view.findViewById(R.id.btVistaPreviaExplicaciónEdit);
        fabBorrarEjercicio= view.findViewById(R.id.fabBorrarEjercicio);
        fabEditEjercicio= view.findViewById(R.id.fabEditEjercicio);
        spCategoria= view.findViewById(R.id.spCategoria);
        spCategoria.setEnabled(false);
        spCategoria.setClickable(false);
        etDescripcionEjercicioEdit= view.findViewById(R.id.etDescripcionEjercicioEdit);
        etURLExplicacionEdit= view.findViewById(R.id.etURLExplicacionEdit);

        Bundle bundle = getArguments();
        vmDetalle.obtenerEjercicio(bundle);
        Ejercicio ejercicio= (Ejercicio) bundle.getSerializable("ejercicio");
        vmDetalle.cargarSpinerCategoria(spCategoria,view,ejercicio.getCategoria().getDescripcion().toString());


        btVistaPreviaExplicaciónEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url= comprobarURL(etURLExplicacionEdit.getText().toString(),getContext());
                wbVideoExplicacionEdit.loadData(url,"text/html","UTF-8");
            }
        });
        fabEditEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ejercicio e=(Ejercicio) bundle.getSerializable("ejercicio");
                ejercicio.setDescripcion(etDescripcionEjercicioEdit.getText().toString());
                ejercicio.setCategoriaId(categoria.getId());
                ejercicio.setExplicacion(etURLExplicacionEdit.getText().toString());
                vmDetalle.actualizarEjercicio(textoBoton,ejercicio);
                textoBoton= "GUARDAR";
            }
        });
        fabBorrarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Eliminar ejercicio")
                        .setMessage("¿Seguro desea eliminar el ejercicio?")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                //Ejercicio ejercicio=(Ejercicio) bundle.getSerializable("ejercicio");
                                vmDetalle.eliminarEjercicio(ejercicio.getId());
                                Navigation.findNavController(view).navigate(R.id.nav_ejercicios);
                            }
                        })

                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface di,int i){
                                Navigation.findNavController(view).navigate(R.id.nav_detalleEjercicio);
                            }
                        }).show();
            }
        });
    }
    public String comprobarURL(String textoUrl, Context context){
        String html;
        String [] url= textoUrl.split("/");
        Log.d("url",url[2]);
        if(url[2].equals("youtu.be")){
            html= "<iframe width='100%' src='https://www.youtube.com/embed/"+url[url.length-1]+"' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>";
        }
        else if (url[2].equals("www.youtube.com")){
            String urlNavegador= textoUrl;
            html= "<iframe width='100%' src='"+urlNavegador.replace("watch?v=","embed/")+"' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>";
        }
        else{
            html= "<iframe width='100%' src='' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>";
            Toast.makeText(context, "Ingrese URL valida de Youtube.", Toast.LENGTH_SHORT).show();
        }
        //wbVideoExplicacion.loadData(html,"text/html","UTF-8");
        return html;
    }


}