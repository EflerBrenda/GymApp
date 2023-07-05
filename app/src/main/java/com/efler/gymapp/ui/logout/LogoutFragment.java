package com.efler.gymapp.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efler.gymapp.Login.LoginActivity;
import com.efler.gymapp.R;
import com.efler.gymapp.request.ApiRetrofit;

public class LogoutFragment extends Fragment {
    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_logout, container, false);
        muestraDialog(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
    }

    public void muestraDialog(View view){

        new AlertDialog.Builder(view.getContext())
                .setTitle("Cerrar sesión")
                .setMessage("Seguro desea cerrar la sesión?")
                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        ApiRetrofit.logOut(view.getContext());
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);
                        System.exit(0);
                    }
                })

                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        Navigation.findNavController(view).navigate(R.id.nav_anuncios);
                    }
                }).show();


    }

}