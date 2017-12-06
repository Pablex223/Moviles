package com.example.javie.proyecto;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Usuario;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearUsuario extends Fragment {

    Button btnCrearCuenta;
    EditText txtUsuario,txtNombre,txtApellido, txtApellido2, txtEdad, txtEmail, txtContrasena;
    TextView linkLogin;
    private int Modificar_Flag = 0 ;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CONTRASENA = "contrasenaKey";
    public static final String EMAIL = "emailKey";
    SharedPreferences sharedpreferences;

    public CrearUsuario() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_usuario, container, false);
        btnCrearCuenta = (Button) view.findViewById(R.id.btnCrearCuenta);
        txtUsuario = (EditText) view.findViewById(R.id.txtUsuario);
        txtNombre = (EditText) view.findViewById(R.id.txtNombre);
        txtApellido = (EditText) view.findViewById(R.id.txtApellod);
        txtApellido2 = (EditText) view.findViewById(R.id.txtApellido2);

        txtEdad = (EditText) view.findViewById(R.id.txtEdad);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtContrasena = (EditText) view.findViewById(R.id.txtContrasena);
        linkLogin = (TextView) view.findViewById(R.id.linkLogin);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        linkLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getActivity().getSupportFragmentManager();
                IngresarUsuario ingresarUsuario = new IngresarUsuario();
                manager.beginTransaction().replace(R.id.contenedor,
                        ingresarUsuario,
                        ingresarUsuario.getTag()).commit();

            }
        });
        btnCrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(verificarUsuario())
                    new JSONCrearUsuario().execute("http://172.17.28.235:8080/AutServlet/as");
                else
                    Toast.makeText(getActivity(), "Por favor, complete los campos",Toast.LENGTH_SHORT).show();

            }
        });
        llenarCamposCuentaModificar();
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

    private void llenarCamposCuentaModificar(){
        //Agregar los demas campos...
        String emailUsuario = sharedpreferences.getString(EMAIL, null);
        if(emailUsuario != null) {
            txtUsuario.setText(emailUsuario);
            btnCrearCuenta.setText("GUARDAR CAMBIOS");
            Modificar_Flag = 1;
        }
    }

    public class JSONCrearUsuario extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);


                JSONObject jo = new JSONObject();
                jo.put("usuario", txtUsuario.getText());
                jo.put("nombre", txtNombre.getText());
                jo.put("pa", txtApellido.getText());
                jo.put("sa", txtApellido2.getText());
                jo.put("edad", Integer.valueOf(txtEdad.getText().toString()));
                jo.put("cont", txtContrasena.getText());
                jo.put("correo", txtEmail.getText());



                JSONObject jo1 = new JSONObject();
                if(Modificar_Flag == 1 )
                    jo1.put("action", "editarUsuario");
                else
                    jo1.put("action", "nuevoUsuario");
                jo1.put("data", jo);

                Log.i("JSON", jo1.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jo1.toString());

                os.flush();
                os.close();
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();


                String line = "";
                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }
                String resultado;
                JSONObject respuesta = new JSONObject(buffer.toString());
                Log.i("RESPONSE", String.valueOf(buffer.toString()));
                if(respuesta.getBoolean("success"))
                    resultado = "true";
                else resultado = "false";


                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());



                conn.disconnect();

                return resultado;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result.equalsIgnoreCase("true")){
                if(Modificar_Flag ==1 )
                    Toast.makeText(getActivity(), "Se modfico el usuario efectivamente",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(), "Se ingreso el usuario efectivamente",Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(getActivity(), "Usuario no ingresado",Toast.LENGTH_SHORT).show();
        }
    }

}
