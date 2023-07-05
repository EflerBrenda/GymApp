package com.efler.gymapp.Login;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.efler.gymapp.R;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel vmLogin;
    private EditText etUsuario, etPassword;
    private Button btLogin,btReestablecerPass;
    private TextView tvError;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_login);
        inicializarVista();
        vmLogin = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        vmLogin.getmutableError().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                tvError.setVisibility(visibility);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, 1000);
        }
    }


    public void inicializarVista() {
        btLogin = findViewById(R.id.btLogin);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btReestablecerPass= findViewById(R.id.btReestablecerPass);
        tvError = findViewById(R.id.tvError);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vmLogin.login(
                        etUsuario.getText().toString(),
                        etPassword.getText().toString()
                );
            }
        });
        etUsuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvError.setVisibility(View.INVISIBLE);
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvError.setVisibility(View.INVISIBLE);
            }
        });
        btReestablecerPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsuario.getText().toString().equals("") || etUsuario.getText().toString()==null ){
                    Toast.makeText(context, "El campo e-mail no debe ser vacio.", Toast.LENGTH_SHORT).show();
                }
                else{
                    vmLogin.restablecerPassword(etUsuario.getText().toString());
                }
            }
        });


    }
}