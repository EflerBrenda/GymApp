package com.efler.gymapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.efler.gymapp.modelo.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.efler.gymapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView tvNombreApellido,tvEmailMenu;
    private MainViewModel mainViewModel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_anuncios, R.id.nav_perfil,R.id.nav_asistencia,R.id.nav_anuncios,R.id.nav_ejercicios,R.id.nav_alumnos,R.id.nav_pago,R.id.nav_rutinas,R.id.nav_planes,R.id.nav_mirutina,R.id.nav_logout,R.id.nav_profesores)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        mainViewModel.getMutablePerfil().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {

                tvNombreApellido.setText(usuario.getNombre() +" "+ usuario.getApellido());
                tvEmailMenu.setText(usuario.getEmail());
                if(usuario.getRolId() == 3 ) {
                    navigationView.getMenu().findItem(R.id.nav_ejercicios).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_alumnos).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_planes).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_rutinas).setVisible(false);
                }
                else if(usuario.getRolId() == 2){
                    navigationView.getMenu().findItem(R.id.nav_pago).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_profesores).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_mirutina).setVisible(false);
                }
                else if(usuario.getRolId() == 1){
                    navigationView.getMenu().findItem(R.id.nav_mirutina).setVisible(false);
                }
            }
        });
        inicializarVista(navigationView);
        solicitarPermisos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void inicializarVista(NavigationView navigationView){
        View view= navigationView.getHeaderView(0);
        tvNombreApellido=view.findViewById(R.id.tvNombreApellido);
        tvEmailMenu=view.findViewById(R.id.tvEmailMenu);
        mainViewModel.obtenerPerfil();

    }
    private void solicitarPermisos() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }


    }
}