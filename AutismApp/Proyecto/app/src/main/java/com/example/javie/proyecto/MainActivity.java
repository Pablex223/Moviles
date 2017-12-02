package com.example.javie.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextToSpeech t1;

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
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale locSpanish = new Locale("spa", "CR");
                    t1.setLanguage(locSpanish);
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
            AcercaDe acercaDe = new AcercaDe();
            manager.beginTransaction().replace(R.id.contenedor,
                    acercaDe,
                    acercaDe.getTag()).commit();
        } else if (id == R.id.nav_send) {
            Formulario formulario = new Formulario();
            manager.beginTransaction().replace(R.id.contenedor,
                    formulario,
                    formulario.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void speak(String text) {
        t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
//    }

}
