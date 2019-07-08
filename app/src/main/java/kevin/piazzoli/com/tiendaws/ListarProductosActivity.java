package kevin.piazzoli.com.tiendaws;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListarProductosActivity extends AppCompatActivity {

    private ListView lvProductos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos);

        lvProductos = findViewById(R.id.lvProductos);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

        lvProductos.setAdapter(adapter);

        AndroidNetworking.get(Constantes.URL_LISTAR_PRODUCTOS)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.getString("respuesta");
                            if(respuesta.equals("200")){
                                JSONArray arrayProductos = response.getJSONArray("data");
                                for(int i=0;i<arrayProductos.length();i++){
                                    JSONObject jsonProducto = arrayProductos.getJSONObject(i);
                                    String id = jsonProducto.getString("id");
                                    String descripcion = jsonProducto.getString("descripcion");
                                    String precio = jsonProducto.getDouble("precio") + " Nuevos Soles";
                                    String categoria = jsonProducto.getString("categoria");

                                    String dataString = id + "\n" + descripcion + "\n" + precio + "\n" + categoria;
                                    adapter.add(dataString);
                                }
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(ListarProductosActivity.this, "No hay ningun producto disponible.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ListarProductosActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ListarProductosActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
