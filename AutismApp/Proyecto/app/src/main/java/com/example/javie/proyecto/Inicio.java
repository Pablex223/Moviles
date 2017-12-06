package com.example.javie.proyecto;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inicio extends Fragment {

    TextView txtBienvenido;
    Button btnSalir, btnEditarCuenta;
    String emailUsuario = "";
    ProgressBar progressBar;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CONTRASENA = "contrasenaKey";
    public static final String EMAIL = "emailKey";
    SharedPreferences sharedpreferences;
    public Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_inicio, container, false);
        txtBienvenido = (TextView) view.findViewById(R.id.txtBienvenido);
        btnSalir = (Button) view.findViewById(R.id.btnSalir);
        btnEditarCuenta = (Button) view.findViewById(R.id.btnEditarCuenta);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        emailUsuario = sharedpreferences.getString(EMAIL, null);
        if(emailUsuario != null) txtBienvenido.setText("¡BIENVENIDO " + emailUsuario + "!");


        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getActivity(),"Saliendo...",Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.VISIBLE);// To Show ProgressBar

                //Espera 3 segundos para cambiar de pantalla
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        //Second fragment after 5 seconds appears
                        progressBar.setVisibility(View.INVISIBLE);
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        IngresarUsuario ingresarUsuario = new IngresarUsuario();
                        manager.beginTransaction().replace(R.id.contenedor,
                                ingresarUsuario,
                                ingresarUsuario.getTag()).commit();
                    }
                };
                handler.postDelayed(runnable, 3000);

            }
        });
        btnEditarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                CrearUsuario crearUsuario = new CrearUsuario();
                manager.beginTransaction().replace(R.id.contenedor,
                        crearUsuario,
                        crearUsuario.getTag()).commit();
            }
        });
        return view;
    }

}
