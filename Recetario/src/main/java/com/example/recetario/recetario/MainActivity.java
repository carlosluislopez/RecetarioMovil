package com.example.recetario.recetario;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import android.content.res.Configuration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recetario.recetario.R;


//import org.ksoap2.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

//import com.f;
//import com.google.gson.reflect.TypeToken;



//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends Activity {

    ImageView imagenSeleccionada;
    Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Integer[] imagenes = { R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,
                                     R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
                                     R.drawable.sample_6, R.drawable.sample_7
                                   };
        GridView gridView = (GridView) findViewById(R.id.grdRecetas);
        //gridView.setAdapter(new ImageAdapter(this));

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
        gridView.setAdapter(new GalleryAdapter(this, imagenes));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(MainActivity.this, ((TextView) view.findViewById(R.id.textView1)).getText() + " Seleccionada", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // dentro de este listener difinimos la función que se ejecuta al
            // hacer click en un item
            // el metodo pertenece a AdapterView, es decir, es
            // AdapterView.OnItemClickListener
            // dentro de este, tenemos el método onItemClick, que es el que se
            // invoca al pulsar un item del AdapterView
            // esa función recibe el objeto padre, que es un adapterview en el
            // que se ha pulasdo, una vista, que es el elemento sobre el que se
            // ha pulsado,
            // una posicion, que es la posicion del elemento dentro del adapter,
            // y un id, que es el id de fila del item que se ha pulsado
            public void onItemClick(AdapterView<?> parent, View v,int position, long id)
            {
                // Toast para mostrar un mensaje. Escribe el nombre de tu clase
                // si no la llamaste MainActivity.
                // Al hacer click, esta mensaje muestra la posición
                Toast.makeText(MainActivity.this, "Elemento" + position, Toast.LENGTH_SHORT).show();

            }
        });
        */


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

}