package com.example.javie.proyecto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    public IngresarUsuario() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingresar_usuario, container, false);
        btnIngresar = (Button) view.findViewById(R.id.btnIngresar);
        txtUsuarioIngresar = (EditText) view.findViewById(R.id.txtUsuarioIngresar);
        txtContrasenaIngresar = (EditText) view.findViewById(R.id.txtContrasenaIngresar);
        linkCrearCuenta = (TextView) view.findViewById(R.id.linkCrearCuenta);
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
                    Toast.makeText(getActivity(), nuevoUsuario.toString(),Toast.LENGTH_SHORT).show();
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
