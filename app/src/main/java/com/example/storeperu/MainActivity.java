package com.example.storeperu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnListar;
    private Button btnBuscar;
    private Button btnRegistrar;
    private Button btnGestionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListar = findViewById(R.id.btnListar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnGestionar = findViewById(R.id.btnGestionar);

        btnListar.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ListarActivity.class));
        });

        btnBuscar.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BuscarActivity.class));
        });

        btnRegistrar.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegistrarActivity.class));
        });

        btnGestionar.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, GestionarActivity.class));
        });
    }
}