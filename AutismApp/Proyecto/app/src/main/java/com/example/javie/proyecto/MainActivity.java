package com.example.javie.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.javie.proyecto.AccesoDatos.DatabaseHelper;
import com.example.javie.proyecto.AccesoDatos.DbBitmapUtility;
import com.example.javie.proyecto.Entidades.Categoria;
import com.example.javie.proyecto.Entidades.Pictograma;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextToSpeech t1;
    DatabaseHelper myDb;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CONTRASENA = "contrasenaKey";
    public static final String EMAIL = "emailKey";
    String emailUsuario = "";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale locSpanish = new Locale("spa", "MEX");
                    //t1.setLanguage(locSpanish);
                    t1.setLanguage(Locale.US);
                }
            }
        });
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager manager = getSupportFragmentManager();

        IngresarUsuario ingresarUsuario = new IngresarUsuario();
        manager.beginTransaction().replace(R.id.contenedor,
                ingresarUsuario,
                ingresarUsuario.getTag()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager manager = getSupportFragmentManager();
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            emailUsuario = sharedpreferences.getString(EMAIL, null);
            if(emailUsuario != null) {
                Inicio inicio = new Inicio();
                manager.beginTransaction().replace(R.id.contenedor,
                        inicio,
                        inicio.getTag()).commit();
            } else {
                IngresarUsuario ingresarUsuario = new IngresarUsuario();
                manager.beginTransaction().replace(R.id.contenedor,
                        ingresarUsuario,
                        ingresarUsuario.getTag()).commit();
            }
        } else if (id == R.id.nav_gallery) {
            OpcionesPictogramas opcionesPictogramas = new OpcionesPictogramas();
            manager.beginTransaction().replace(R.id.contenedor,
                    opcionesPictogramas,
                    opcionesPictogramas.getTag()).commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Formulario formulario = new Formulario();
            manager.beginTransaction().replace(R.id.contenedor,
                    formulario,
                    formulario.getTag()).commit();
        } else if (id == R.id.nav_send) {
            AcercaDe acercaDe = new AcercaDe();
            manager.beginTransaction().replace(R.id.contenedor,
                    acercaDe,
                    acercaDe.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void speak(String text) {
        //Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
        t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

   //BD
    public boolean insertar(String nombre,  byte[] image, String categoria, String respuesta){
        return myDb.insertData(nombre, image, categoria, respuesta);
    }

    public Bitmap getBitmapImage(String nombre){

        Cursor res = myDb.getPictogramaPorNombre(nombre);
        Bitmap b = null;
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
        }

        while (res.moveToNext()) {
            byte[] image = res.getBlob(2);
            b = DbBitmapUtility.getImage(image);
        }
        return b;
    }

    public  List<Pictograma> getPictogramasPorCategoria(String categoria){
        List<Pictograma> listaPictogramas;
        listaPictogramas = new ArrayList<Pictograma>();
        Cursor res = myDb.getPictogramaPorCategoria(categoria);
        Bitmap b = null;
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
        }
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String nombre = res.getString(1);
            byte[] image = res.getBlob(2);
            b = DbBitmapUtility.getImage(image);
            String cate = res.getString(3);
            String respuesta = res.getString(4);
            Pictograma nuevo = new Pictograma(id,nombre,b,new Categoria(cate), respuesta);
            listaPictogramas.add(nuevo);
        }
        return listaPictogramas;
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
