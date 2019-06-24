package kevin.piazzoli.com.tiendaws;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarProductoActivity extends AppCompatActivity {

    private EditText edtCodigo;
    private EditText edtDescripcion;
    private EditText edtPrecio;
    private EditText edtCategoria;

    private Button btnActualizar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_producto);
        edtCodigo = findViewById(R.id.edtCodigo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtCategoria = findViewById(R.id.edtCategoria);

        btnActualizar = findViewById(R.id.btnActualizar);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarProducto();
            }
        });

    }

    private void actualizarProducto(){
        if(isValidarCampos()){

            String codigo = edtCodigo.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
            String precio = edtPrecio.getText().toString();
            String categoria = edtCategoria.getText().toString();

            Map<String,String> datos = new HashMap<>();
            datos.put("codigo",codigo);
            datos.put("descripcion",descripcion);
            datos.put("precio",precio);
            datos.put("categoria",categoria);
            JSONObject jsonData = new JSONObject(datos);

            AndroidNetworking.post(Constantes.URL_ACTUALIZAR_PRODUCTO)
                    .addJSONObjectBody(jsonData)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String estado = response.getString("estado");
                                String error = response.getString("error");
                                Toast.makeText(ActualizarProductoActivity.this, estado, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(ActualizarProductoActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(ActualizarProductoActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(ActualizarProductoActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(this, "Existen campos vacios no puedes actualizar.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidarCampos(){
        return !edtCodigo.getText().toString().trim().isEmpty() &&
                !edtDescripcion.getText().toString().trim().isEmpty() &&
                !edtPrecio.getText().toString().trim().isEmpty() &&
                !edtCategoria.getText().toString().trim().isEmpty();
    }

}
