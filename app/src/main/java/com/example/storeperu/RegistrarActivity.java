package com.example.storeperu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegistrarActivity extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtCategoria;
    private EditText edtDescripcion;
    private EditText edtGarantia;
    private EditText edtPrecio;
    private EditText edtStock;

    private Button btnRegistrarProducto;

    private final String URL = "http://192.168.18.93:3000/productos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Conectar los campos del XML
        edtNombre = findViewById(R.id.edtNombre);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtGarantia = findViewById(R.id.edtGarantia);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtStock = findViewById(R.id.edtStock);

        // Conectar botón
        btnRegistrarProducto = findViewById(R.id.btnRegistrarProducto);

        // Acción del botón
        btnRegistrarProducto.setOnClickListener(v -> registrarProducto());
    }

    private void registrarProducto() {

        // Obtener los datos ingresados
        String nombre = edtNombre.getText().toString().trim();
        String categoria = edtCategoria.getText().toString().trim();
        String descripcion = edtDescripcion.getText().toString().trim();
        String garantiaTexto = edtGarantia.getText().toString().trim();
        String precioTexto = edtPrecio.getText().toString().trim();
        String stockTexto = edtStock.getText().toString().trim();

        // Validar campos vacíos
        if (nombre.isEmpty() ||
                categoria.isEmpty() ||
                descripcion.isEmpty() ||
                garantiaTexto.isEmpty() ||
                precioTexto.isEmpty() ||
                stockTexto.isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        try {

            // Convertir valores numéricos
            int garantia = Integer.parseInt(garantiaTexto);
            double precio = Double.parseDouble(precioTexto);
            int stock = Integer.parseInt(stockTexto);

            // Crear JSON para enviar al Web Service
            JSONObject datos = new JSONObject();

            datos.put("nombre", nombre);
            datos.put("categoria", categoria);
            datos.put("descripcion", descripcion);
            datos.put("garantia", garantia);
            datos.put("precio", precio);
            datos.put("stock", stock);

            // Crear cola de Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            // POST /productos
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    datos,

                    response -> {

                        Toast.makeText(
                                RegistrarActivity.this,
                                "Producto registrado correctamente",
                                Toast.LENGTH_SHORT
                        ).show();

                        limpiarCampos();
                    },

                    error -> {

                        Toast.makeText(
                                RegistrarActivity.this,
                                "Error al registrar producto",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
            );

            requestQueue.add(request);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Datos inválidos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void limpiarCampos() {

        edtNombre.setText("");
        edtCategoria.setText("");
        edtDescripcion.setText("");
        edtGarantia.setText("");
        edtPrecio.setText("");
        edtStock.setText("");
    }
}