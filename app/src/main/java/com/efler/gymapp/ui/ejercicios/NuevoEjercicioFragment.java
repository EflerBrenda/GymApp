package com.efler.gymapp.ui.ejercicios;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Ejercicio;
import com.efler.gymapp.request.ApiRetrofit;
import com.efler.gymapp.ui.anuncios.NuevoAnuncioViewModel;

public class NuevoEjercicioFragment extends Fragment {
    private Context context;
    private NuevoEjercicioViewModel mViewModel;
    private View view;
    private WebView wbVideoExplicacion;
    private EditText etDescripcionNuevoEjercicio,etURLExplicacion;
    private TextView tvCategoriaEjercicio;
    private Button btVistaPreviaExplicación,btAgregarEjercicio;
    private Bundle tabCategoria;

    public static NuevoEjercicioFragment newInstance() {
        return new NuevoEjercicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle ejercicio) {
        tabCategoria= getArguments();
        mViewModel = new ViewModelProvider(this).get(NuevoEjercicioViewModel.class);
        view = inflater.inflate(R.layout.fragment_nuevo_ejercicio, container, false);
        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view) {
        context=getContext();
        wbVideoExplicacion = view.findViewById(R.id.wbVideoExplicacion);
        wbVideoExplicacion.setWebViewClient(new WebViewClient());
        WebSettings settings = wbVideoExplicacion.getSettings();
        settings.setJavaScriptEnabled(true);
        etDescripcionNuevoEjercicio = view.findViewById(R.id.etDescripcionNuevoEjercicio);
        etURLExplicacion= view.findViewById(R.id.etURLExplicacion);
        btVistaPreviaExplicación = view.findViewById(R.id.btVistaPreviaExplicación);
        btAgregarEjercicio = view.findViewById(R.id.btAgregarEjercicio);
        tvCategoriaEjercicio = view.findViewById(R.id.tvCategoriaEjercicio);
        tvCategoriaEjercicio.setText(tabCategoria.getString("categorias"));

        btAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ejercicio ejercicio= new Ejercicio();
                ejercicio.setDescripcion(etDescripcionNuevoEjercicio.getText().toString());
                ejercicio.setCategoriaId(tabCategoria.getInt("idCategorias"));
                ejercicio.setExplicacion(etURLExplicacion.getText().toString());
                mViewModel.crearEjercicio(ejercicio,view);
            }
        });
        btVistaPreviaExplicación.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String html;
                String [] url= etURLExplicacion.getText().toString().split("/");
                Log.d("url",url[2]);
                if(url[2].equals("youtu.be")){
                    html= "<iframe width='100%' src='https://www.youtube.com/embed/"+url[url.length-1]+"' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>";
                }
                else if (url[2].equals("www.youtube.com")){
                    String urlNavegador= etURLExplicacion.getText().toString();
                    html= "<iframe width='100%' src='"+urlNavegador.replace("watch?v=","embed/")+"' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>";
                }
                else{
                    html= "<iframe width='100%' src='' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>";
                    Toast.makeText(context, "Ingrese URL valida de Youtube.", Toast.LENGTH_SHORT).show();
                }
                wbVideoExplicacion.loadData(html,"text/html","UTF-8");
            }
        });

    }

}