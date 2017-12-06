package com.example.javie.proyecto;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.javie.proyecto.Entidades.Usuario;
import com.example.javie.proyecto.Utilidades.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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
        final MainActivity myActivity = (MainActivity) getActivity();
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                  //new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt", "0");
                if(verificarUsuario())
                    new JSONTask().execute("http://192.168.1.107:8080/AutServlet/as");
               else
                    Toast.makeText(getActivity(), "Por favor, complete los campos",Toast.LENGTH_SHORT).show();

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

    public class JSONTask extends AsyncTask<String, String, String> {

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

                JSONObject obj = new JSONObject();
               obj.put("id", "1");
               obj.put("name", "myname");

               JSONObject jo = new JSONObject();
               jo.put("usuario", txtUsuarioIngresar.getText());
               jo.put("contra", txtContrasenaIngresar.getText());

               JSONObject jo1 = new JSONObject();
               jo1.put("action", "login");
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
                if(respuesta.getBoolean("login"))
                    resultado = "true";
                else resultado = "false";

                Log.i("RESPONSE", String.valueOf(buffer.toString()));
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
                Toast.makeText(getActivity(), "Iniciando sesión...",Toast.LENGTH_SHORT).show();
                String nombre = txtUsuarioIngresar.getText().toString();
                    String contrasenna = txtContrasenaIngresar.getText().toString();
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(EMAIL, nombre);
                    editor.putString(CONTRASENA, contrasenna);
                    editor.commit();

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
            else
                Toast.makeText(getActivity(), "Usuario incorrecto",Toast.LENGTH_SHORT).show();
            }
        }


}
