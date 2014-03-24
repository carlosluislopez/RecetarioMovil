package recetariomovil.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import android.content.res.Configuration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import recetariomovil.app.R;



public class MainActivity extends Activity {

    ImageView imagenSeleccionada;
    Gallery gallery;
    public static String rslt="";
    List<Recipe> recetas;
    BaseAdapter mMyAdapter;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*
        final Integer[] imagenes = { R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,
                R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };
        */
        this.gridView = (GridView) findViewById(R.id.grdRecetas);
        cargarDatos();

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView img = (ImageView) view.findViewById(R.id.imgReceta);
                TextView txtCategoria = (TextView) view.findViewById(R.id.txtCategoria);
                TextView txtReceta = (TextView) view.findViewById(R.id.txtReceta);
                TextView txtUsuario = (TextView) view.findViewById(R.id.txtUsuario);

                Drawable d = img.getDrawable();
                byte[] byteArray = null;
                try{
                    Bitmap bmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    byteArray = stream.toByteArray();

                    Canvas canvas = new Canvas(bmap);
                    d.draw(canvas);
                }catch (Exception ex){}

                lanzar(view, recetas.get(position), byteArray);

                //Toast.makeText(MainActivity.this, ((TextView) view.findViewById(R.id.txtCategoria)).getText() + " Seleccionada", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void lanzar(View view, Recipe Receta, byte[] byteArray) {
        Intent i = new Intent(this, DetailActivity.class );
        i.putExtra("Categoria", Receta.Categoria);
        i.putExtra("Receta", Receta.Nombre);
        i.putExtra("Descripcion", Receta.Descripcion);
        i.putExtra("Usuario", Receta.Usuario);
        i.putExtra("UsuarioNombre", Receta.UsuarioNombre);
        i.putExtra("Rating", Receta.UsuarioNombre);
        i.putExtra("data", Receta.Foto);
        i.putExtra("Longitud", Receta.Longitud);
        startActivity(i);
    }

    public void cargarDatos(){
        //el número de columnas se calculará en función del tamaño de pantalla y la posición
        boolean bigScreen = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (bigScreen)
            {
                this.gridView.setNumColumns(3);
            }
            else
            {
                this.gridView.setNumColumns(2);
            }
        }
        else
        {
            if (bigScreen)
            {
                this.gridView.setNumColumns(2);
            }
            else
            {
                this.gridView.setNumColumns(1);
            }
        }
        traerDatos();
        this.mMyAdapter = new GalleryAdapter(this, recetas);
        gridView.setAdapter(mMyAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void onClick(View arg0)
    {
        this.traerDatos();
        ((GalleryAdapter)this.mMyAdapter).Recetas = this.recetas;
        final AlertDialog ad = new AlertDialog.Builder(this).create();
        try{
            this.mMyAdapter.notifyDataSetChanged();
        }catch (Exception ex){
            ad.setTitle("Error!");
            ad.setMessage(ex.toString());
            ad.show();
        }
    }

    public void traerDatos(){
        final AlertDialog ad = new AlertDialog.Builder(this).create();
        Caller c = new Caller();
        try
        {
            EditText ed1=(EditText)findViewById(R.id.txtBuscador);

            rslt="START";
            c.search = ed1.getText().toString();
            c.join();
            c.start();
            while(rslt=="START")
            {
                try
                {
                    Thread.sleep(10);
                }
                catch(Exception ex)
                {
                    ed1.setText(ex.getMessage());
                }
            }
            //ad.setTitle("RESULT OF ADD of " + a + " and "+b);
            //ad.setMessage(rslt);
        }catch(Exception ex)
        {
            ad.setTitle("Error!");
            ad.setMessage(ex.toString());
            ad.show();
        }
        this.recetas = c.recetas;

        //cargarDatos((GridView) findViewById(R.id.grdRecetas));
    }

    public class Caller  extends Thread
    {
        public CallSoap cs;
        public String search;
        public List<Recipe> recetas;

        public void run()
        {
            try
            {
                cs = new CallSoap();
                this.recetas = cs.Call(search);
                MainActivity.rslt = "Datos";
            }
            catch(Exception ex)
            {
                MainActivity.rslt = ex.toString();
            }
        }
    }

}
