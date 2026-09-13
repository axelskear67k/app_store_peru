package com.example.storeperu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class BuscarActivity extends AppCompatActivity {

    private EditText edtIdProducto;
    private Button btnBuscarProducto;
    private TextView txtResultadoBuscar;

    private final String URL = "http://192.168.18.93:3000/productos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        edtIdProducto = findViewById(R.id.edtIdProducto);
        btnBuscarProducto = findViewById(R.id.btnBuscarProducto);
        txtResultadoBuscar = findViewById(R.id.txtResultadoBuscar);

        btnBuscarProducto.setOnClickListener(v -> buscarProducto());
    }

    private void buscarProducto() {

        String id = edtIdProducto.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(
                    this,
                    "Ingrese un ID",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + id,
                null,

                response -> {
                    try {

                        JSONObject producto = response.getJSONObject("data");

                        String resultado =
                                "ID: " + producto.getInt("id") + "\n\n" +
                                        "Nombre: " + producto.getString("nombre") + "\n\n" +
                                        "Categoría: " + producto.getString("categoria") + "\n\n" +
                                        "Descripción: " + producto.getString("descripcion") + "\n\n" +
                                        "Garantía: " + producto.getInt("garantia") + " meses\n\n" +
                                        "Precio: S/ " + producto.getString("precio") + "\n\n" +
                                        "Stock: " + producto.getInt("stock");

                        txtResultadoBuscar.setText(resultado);

                    } catch (Exception e) {

                        Toast.makeText(
                                BuscarActivity.this,
                                "Error al procesar el producto",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },

                error -> {

                    txtResultadoBuscar.setText("Producto no encontrado");

                    Toast.makeText(
                            BuscarActivity.this,
                            "Producto no encontrado",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        requestQueue.add(request);
    }
}