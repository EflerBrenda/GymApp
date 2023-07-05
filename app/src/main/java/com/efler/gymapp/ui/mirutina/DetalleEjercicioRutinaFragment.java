package com.efler.gymapp.ui.mirutina;

import androidx.lifecycle.Observer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.efler.gymapp.R;
import com.efler.gymapp.modelo.Ejercicio;

public class DetalleEjercicioRutinaFragment extends Fragment {

    private DetalleEjercicioRutinaViewModel mViewModel;
    private WebView wbVideoExplicacionAlumno;
    private TextView tvDescripcionEjercicioAlumno,tvCategoriaEjercicioAlumno;
    private View view;

    public static DetalleEjercicioRutinaFragment newInstance() {
        return new DetalleEjercicioRutinaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleEjercicioRutinaViewModel.class);
        view =inflater.inflate(R.layout.fragment_detalle_ejercicio_rutina, container, false);
        mViewModel.getMutableEjercicio().observe(getViewLifecycleOwner(), new Observer<Ejercicio>() {
            @Override
            public void onChanged(Ejercicio ejercicio) {
                tvDescripcionEjercicioAlumno.setText("Ejercicio: "+ejercicio.getDescripcion());
                tvCategoriaEjercicioAlumno.setText("Categoría: "+ejercicio.getCategoria().getDescripcion());
                String url= comprobarURL(ejercicio.getExplicacion(),getContext());
                wbVideoExplicacionAlumno.loadData(url,"text/html","UTF-8");
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        wbVideoExplicacionAlumno= view.findViewById(R.id.wbVideoExplicacionAlumno);
        wbVideoExplicacionAlumno.setWebViewClient(new WebViewClient());
        WebSettings settings = wbVideoExplicacionAlumno.getSettings();
        settings.setJavaScriptEnabled(true);
        tvDescripcionEjercicioAlumno= view.findViewById(R.id.tvDescripcionEjercicioAlumno);
        tvCategoriaEjercicioAlumno= view.findViewById(R.id.tvCategoriaEjercicioAlumno);
        Bundle bundle= getArguments();
        mViewModel.obtenerEjercicio(bundle);
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