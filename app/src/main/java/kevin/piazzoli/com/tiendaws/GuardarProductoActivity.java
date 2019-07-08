package kevin.piazzoli.com.tiendaws;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class GuardarProductoActivity extends AppCompatActivity {

    private EditText edtCodigo;
    private EditText edtDescripcion;
    private EditText edtPrecio;
    private EditText edtCategoria;

    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_producto);
        AndroidNetworking.initialize(getApplicationContext());

        edtCodigo = findViewById(R.id.edtCodigo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtCategoria = findViewById(R.id.edtCategoria);

        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });

    }

    private void guardarProducto(){
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

            AndroidNetworking.post(Constantes.URL_INSERTAR_PRODUCTO)
                    .addJSONObjectBody(jsonData)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String estado = response.getString("estado");
                                String error = response.getString("error");
                                Toast.makeText(GuardarProductoActivity.this, estado, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(GuardarProductoActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(GuardarProductoActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(GuardarProductoActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "No se puede insertar un producto si existen campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    //devuelve verdadero si es que no hay campos vacios
    //devuelve falso si es que hay como minimo un campo vacio
    //"     01  " => "01"
    private boolean isValidarCampos(){
        return !edtCodigo.getText().toString().trim().isEmpty() &&
                !edtDescripcion.getText().toString().trim().isEmpty() &&
                !edtPrecio.getText().toString().trim().isEmpty() &&
                !edtCategoria.getText().toString().trim().isEmpty();
    }

}
