package com.example.storeperu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListarActivity extends AppCompatActivity {

    private Button btnCargarProductos;
    private TextView txtResultadoListar;

    private final String URL = "http://192.168.18.93:3000/productos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        btnCargarProductos = findViewById(R.id.btnCargarProductos);
        txtResultadoListar = findViewById(R.id.txtResultadoListar);

        btnCargarProductos.setOnClickListener(v -> listarProductos());
    }

    private void listarProductos() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,

                response -> {
                    try {

                        JSONArray productos = response.getJSONArray("data");

                        StringBuilder resultado = new StringBuilder();

                        for (int i = 0; i < productos.length(); i++) {

                            JSONObject producto = productos.getJSONObject(i);

                            resultado.append("ID: ")
                                    .append(producto.getInt("id"))
                                    .append("\n");

                            resultado.append("Nombre: ")
                                    .append(producto.getString("nombre"))
                                    .append("\n");

                            resultado.append("Categoría: ")
                                    .append(producto.getString("categoria"))
                                    .append("\n");

                            resultado.append("Descripción: ")
                                    .append(producto.getString("descripcion"))
                                    .append("\n");

                            resultado.append("Garantía: ")
                                    .append(producto.getInt("garantia"))
                                    .append(" meses\n");

                            resultado.append("Precio: S/ ")
                                    .append(producto.getString("precio"))
                                    .append("\n");

                            resultado.append("Stock: ")
                                    .append(producto.getInt("stock"))
                                    .append("\n");

                            resultado.append("-------------------------\n\n");
                        }

                        txtResultadoListar.setText(resultado.toString());

                    } catch (Exception e) {
                        Toast.makeText(
                                ListarActivity.this,
                                "Error al procesar los productos",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },

                error -> Toast.makeText(
                        ListarActivity.this,
                        "Error de conexión con el servidor",
                        Toast.LENGTH_SHORT
                ).show()
        );

        requestQueue.add(request);
    }
}