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

public class ActualizarActivity extends AppCompatActivity {

    private EditText edtIdActualizar;
    private EditText edtNombreActualizar;
    private EditText edtCategoriaActualizar;
    private EditText edtDescripcionActualizar;
    private EditText edtGarantiaActualizar;
    private EditText edtPrecioActualizar;
    private EditText edtStockActualizar;

    private Button btnBuscarActualizar;
    private Button btnActualizarProducto;

    private final String URL = "http://192.168.18.93:3000/productos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        edtIdActualizar = findViewById(R.id.edtIdActualizar);
        edtNombreActualizar = findViewById(R.id.edtNombreActualizar);
        edtCategoriaActualizar = findViewById(R.id.edtCategoriaActualizar);
        edtDescripcionActualizar = findViewById(R.id.edtDescripcionActualizar);
        edtGarantiaActualizar = findViewById(R.id.edtGarantiaActualizar);
        edtPrecioActualizar = findViewById(R.id.edtPrecioActualizar);
        edtStockActualizar = findViewById(R.id.edtStockActualizar);

        btnBuscarActualizar = findViewById(R.id.btnBuscarActualizar);
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto);

        btnBuscarActualizar.setOnClickListener(v -> buscarProducto());

        btnActualizarProducto.setOnClickListener(v -> actualizarProducto());
    }

    private void buscarProducto() {

        String id = edtIdActualizar.getText().toString().trim();

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

                        edtNombreActualizar.setText(
                                producto.getString("nombre")
                        );

                        edtCategoriaActualizar.setText(
                                producto.getString("categoria")
                        );

                        edtDescripcionActualizar.setText(
                                producto.getString("descripcion")
                        );

                        edtGarantiaActualizar.setText(
                                String.valueOf(producto.getInt("garantia"))
                        );

                        edtPrecioActualizar.setText(
                                producto.getString("precio")
                        );

                        edtStockActualizar.setText(
                                String.valueOf(producto.getInt("stock"))
                        );

                        Toast.makeText(
                                ActualizarActivity.this,
                                "Producto encontrado",
                                Toast.LENGTH_SHORT
                        ).show();

                    } catch (Exception e) {

                        Toast.makeText(
                                ActualizarActivity.this,
                                "Error al procesar el producto",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },

                error -> Toast.makeText(
                        ActualizarActivity.this,
                        "Producto no encontrado",
                        Toast.LENGTH_SHORT
                ).show()
        );

        requestQueue.add(request);
    }

    private void actualizarProducto() {

        String id = edtIdActualizar.getText().toString().trim();
        String nombre = edtNombreActualizar.getText().toString().trim();
        String categoria = edtCategoriaActualizar.getText().toString().trim();
        String descripcion = edtDescripcionActualizar.getText().toString().trim();
        String garantiaTexto = edtGarantiaActualizar.getText().toString().trim();
        String precioTexto = edtPrecioActualizar.getText().toString().trim();
        String stockTexto = edtStockActualizar.getText().toString().trim();

        if (id.isEmpty() ||
                nombre.isEmpty() ||
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

            int garantia = Integer.parseInt(garantiaTexto);
            double precio = Double.parseDouble(precioTexto);
            int stock = Integer.parseInt(stockTexto);

            JSONObject datos = new JSONObject();

            datos.put("nombre", nombre);
            datos.put("categoria", categoria);
            datos.put("descripcion", descripcion);
            datos.put("garantia", garantia);
            datos.put("precio", precio);
            datos.put("stock", stock);

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    URL + id,
                    datos,

                    response -> {

                        Toast.makeText(
                                ActualizarActivity.this,
                                "Producto actualizado correctamente",
                                Toast.LENGTH_SHORT
                        ).show();
                    },

                    error -> Toast.makeText(
                            ActualizarActivity.this,
                            "Error al actualizar producto",
                            Toast.LENGTH_SHORT
                    ).show()
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
}