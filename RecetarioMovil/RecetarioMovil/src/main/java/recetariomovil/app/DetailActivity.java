package recetariomovil.app;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.TextView;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        Bitmap bmap = BitmapFactory.decodeByteArray(bundle.getByteArray("data"), 0, bundle.getByteArray("data").length);

        ImageView img = (ImageView)findViewById(R.id.imgFoto);
        img.setImageBitmap(bmap);

        TextView txtCategoria = (TextView)findViewById(R.id.txtCategoria);
        txtCategoria.setText(bundle.get("Categoria").toString());


        TextView txtReceta = (TextView)findViewById(R.id.txtReceta);
        txtReceta.setText(bundle.get("Receta").toString());

        TextView txtDescripcion = (TextView)findViewById(R.id.txtDescripcion);
        txtDescripcion.setText(bundle.get("Descripcion").toString());

        String usuario = bundle.get("Usuario").toString() + " - " + bundle.get("UsuarioNombre").toString();
        TextView txtUsuario = (TextView)findViewById(R.id.txtUsuario);
        txtUsuario.setText(usuario);

        Button btnCerrar = (Button)findViewById(R.id.btnVolver);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
