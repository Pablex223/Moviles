package com.example.javie.proyecto;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngresarUsuario extends Fragment {

    Button btnIngresar;
    EditText txtUsuarioIngresar, txtContrasenaIngresar;
    TextView linkCrearCuenta;
    ProgressBar progressBar;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CONTRASENA = "contrasenaKey";
    public static final String EMAIL = "emailKey";
    SharedPreferences sharedpreferences;



    public IngresarUsuario() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ingresar_usuario, container, false);
        btnIngresar = (Button) view.findViewById(R.id.btnIngresar);
        txtUsuarioIngresar = (EditText) view.findViewById(R.id.txtUsuarioIngresar);
        txtContrasenaIngresar = (EditText) view.findViewById(R.id.txtContrasenaIngresar);
        linkCrearCuenta = (TextView) view.findViewById(R.id.linkCrearCuenta);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        linkCrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getActivity().getSupportFragmentManager();
                CrearUsuario crearUsuario = new CrearUsuario();
                manager.beginTransaction().replace(R.id.contenedor,
                        crearUsuario,
                        crearUsuario.getTag()).commit();

            }
        });
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(verificarUsuario()){
                    Usuario nuevoUsuario = new Usuario();
                    String nombre = txtUsuarioIngresar.getText().toString();
                    String contrasenna = txtContrasenaIngresar.getText().toString();
                    nuevoUsuario.setEmail(nombre);
                    nuevoUsuario.setContrasena(contrasenna);

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(EMAIL, nombre);
                    editor.putString(CONTRASENA, contrasenna);
                    editor.commit();

                    Toast.makeText(getActivity(), nuevoUsuario.toString(),Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.VISIBLE);// To Show ProgressBar

                   //Espera 3 segundos para cambiar de pantalla
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            //Second fragment after 5 seconds appears
                            progressBar.setVisibility(View.INVISIBLE);
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            Inicio inicio = new Inicio();
                            manager.beginTransaction().replace(R.id.contenedor,
                                    inicio,
                                    inicio.getTag()).commit();
                        }
                    };
                    handler.postDelayed(runnable, 3000);

                }
               else{
                    Toast.makeText(getActivity(), "Por favor, complete los campos",Toast.LENGTH_SHORT).show();
               }

            }
        });

        return view;
    }

    private boolean verificarUsuario(){
        String nombre = txtUsuarioIngresar.getText().toString();
        String contrasenna = txtContrasenaIngresar.getText().toString();
        if(TextUtils.isEmpty(nombre)) {
            txtUsuarioIngresar.setError("Ingrese un usuario!");
            return false;
        }
        else if(TextUtils.isEmpty(contrasenna)) {
            txtContrasenaIngresar.setError("Ingrese una contrasena!");
            return false;
        }
        else return true;
    }


}
