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

public class EliminarActivity extends AppCompatActivity {

    private EditText edtIdEliminar;
    private Button btnBuscarEliminar;
    private Button btnEliminarProducto;
    private TextView txtProductoEliminar;

    private final String URL = "http://192.168.18.93:3000/productos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        edtIdEliminar = findViewById(R.id.edtIdEliminar);
        btnBuscarEliminar = findViewById(R.id.btnBuscarEliminar);
        btnEliminarProducto = findViewById(R.id.btnEliminarProducto);
        txtProductoEliminar = findViewById(R.id.txtProductoEliminar);

        btnBuscarEliminar.setOnClickListener(v -> buscarProducto());

        btnEliminarProducto.setOnClickListener(v -> eliminarProducto());
    }

    private void buscarProducto() {

        String id = edtIdEliminar.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
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
                                        "Precio: S/ " + producto.getString("precio") + "\n\n" +
                                        "Stock: " + producto.getInt("stock");

                        txtProductoEliminar.setText(resultado);

                    } catch (Exception e) {
                        Toast.makeText(
                                EliminarActivity.this,
                                "Error al procesar el producto",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },
                error -> {
                    txtProductoEliminar.setText("Producto no encontrado");

                    Toast.makeText(
                            EliminarActivity.this,
                            "Producto no encontrado",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        requestQueue.add(request);
    }

    private void eliminarProducto() {

        String id = edtIdEliminar.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                URL + id,
                null,
                response -> {

                    Toast.makeText(
                            EliminarActivity.this,
                            "Producto eliminado correctamente",
                            Toast.LENGTH_SHORT
                    ).show();

                    edtIdEliminar.setText("");
                    txtProductoEliminar.setText("Aquí aparecerá el producto");
                },
                error -> Toast.makeText(
                        EliminarActivity.this,
                        "Error al eliminar producto",
                        Toast.LENGTH_SHORT
                ).show()
        );

        requestQueue.add(request);
    }
}