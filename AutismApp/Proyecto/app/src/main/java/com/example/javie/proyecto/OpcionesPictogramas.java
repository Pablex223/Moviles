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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpcionesPictogramas extends Fragment {

    ImageView imgCrear, imgEmpezar;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CONTRASENA = "contrasenaKey";
    public static final String EMAIL = "emailKey";
    SharedPreferences sharedpreferences;
    public OpcionesPictogramas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_opciones_pictogramas, container, false);
        imgEmpezar = (ImageView) view.findViewById(R.id.imgEmpezar);
        imgCrear = (ImageView) view.findViewById(R.id.imgCrear);

        imgEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                IniciarPictogramas iniciarPictogramas = new IniciarPictogramas();
                manager.beginTransaction().replace(R.id.contenedor,
                        iniciarPictogramas,
                        iniciarPictogramas.getTag()).commit();
            }
        });

        imgCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                CrearPictogramas crearPictogramas = new CrearPictogramas();
                manager.beginTransaction().replace(R.id.contenedor,
                        crearPictogramas,
                        crearPictogramas.getTag()).commit();
            }
        });
        return view;
    }

}
