package kevin.piazzoli.com.tiendaws;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnInsertarProducto;
    private Button btnActualizarProducto;
    private Button btnBorrarProducto;
    private Button btnListarProductos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_menu);

        btnInsertarProducto = findViewById(R.id.btnInsertarProducto);
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto);
        btnBorrarProducto = findViewById(R.id.btnBorrarProducto);
        btnListarProductos = findViewById(R.id.btnListarProductos);

        btnInsertarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irActivityInsertarProducto();
            }
        });

        btnActualizarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irActivityActualizarProducto();
            }
        });

    }


    private void irActivityInsertarProducto(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void irActivityActualizarProducto(){
        Intent intent = new Intent(this,ActualizarProductoActivity.class);
        startActivity(intent);
    }

}
