package com.example.storeperu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GestionarActivity extends AppCompatActivity {

    private Button btnIrActualizar;
    private Button btnIrEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar);

        btnIrActualizar = findViewById(R.id.btnIrActualizar);
        btnIrEliminar = findViewById(R.id.btnIrEliminar);

        btnIrActualizar.setOnClickListener(v -> {
            Intent intent = new Intent(
                    GestionarActivity.this,
                    ActualizarActivity.class
            );
            startActivity(intent);
        });

        btnIrEliminar.setOnClickListener(v -> {
            Intent intent = new Intent(
                    GestionarActivity.this,
                    EliminarActivity.class
            );
            startActivity(intent);
        });
    }
}