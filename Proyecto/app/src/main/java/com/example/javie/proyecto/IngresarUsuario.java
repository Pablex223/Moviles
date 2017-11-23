package com.example.javie.proyecto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngresarUsuario extends Fragment {

    Button btnCrearCuenta;
    EditText txtNombre, txtEmail, txtContrasena;
    public IngresarUsuario() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_usuario, container, false);
        btnCrearCuenta = (Button) view.findViewById(R.id.btnCrearCuenta);
        txtNombre = (EditText) view.findViewById(R.id.txtNombre);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtContrasena = (EditText) view.findViewById(R.id.txtContrasena);
        btnCrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(verificarUsuario()){
                    Usuario nuevoUsuario = new Usuario(txtNombre.getText().toString(),
                            txtEmail.getText().toString(), txtContrasena.getText().toString());
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
        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String contrasenna = txtContrasena.getText().toString();
        if(TextUtils.isEmpty(nombre)) {
            txtNombre.setError("Ingrese un nombre!");
            return false;
        }
        else if(TextUtils.isEmpty(email)) {
            txtEmail.setError("Ingrese un email!");
            return false;
        }
        else if(TextUtils.isEmpty(contrasenna)) {
            txtContrasena.setError("Ingrese una contrasena!");
            return false;
        }
        else return true;
    }


}
