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
import android.widget.AdapterView;
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
        GridView gridView = (GridView) findViewById(R.id.grdRecetas);
        cargarDatos(gridView);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView img = (ImageView) view.findViewById(R.id.imgReceta);

                Drawable d = img.getDrawable();
                Bitmap bmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmap);
                d.draw(canvas);

                //Bitmap bmap = Bitmap.createBitmap(img.getDrawingCache());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                lanzar(view, byteArray);
                //Toast.makeText(MainActivity.this, ((TextView) view.findViewById(R.id.txtCategoria)).getText() + " Seleccionada", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void lanzar(View view, byte[] byteArray) {
        Intent i = new Intent(this, DetailActivity.class );
        i.putExtra("data", byteArray);
        startActivity(i);
    }

    public void cargarDatos(GridView gridView){
        //el número de columnas se calculará en función del tamaño de pantalla y la posición
        boolean bigScreen = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (bigScreen)
            {
                gridView.setNumColumns(3);
            }
            else
            {
                gridView.setNumColumns(2);
            }
        }
        else
        {
            if (bigScreen)
            {
                gridView.setNumColumns(2);
            }
            else
            {
                gridView.setNumColumns(1);
            }
        }
        gridView.setAdapter(new GalleryAdapter(this, recetas));
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
        }
        this.recetas = c.recetas;
        cargarDatos((GridView) findViewById(R.id.grdRecetas));
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
                recetas = cs.Call(search);
                MainActivity.rslt = "Datos";
            }
            catch(Exception ex)
            {
                MainActivity.rslt = ex.toString();
            }
        }
    }

}
