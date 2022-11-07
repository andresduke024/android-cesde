package com.example.empresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UsuariosActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    EditText jetusuario,jetnombre,jetcorreo,jetclave;
    CheckBox jcbactivo;
    RequestQueue rq;
    JsonRequest jrq;
    String usr,nombre,correo,clave,url;
    byte sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        //Ocultar barra por defecto, asociar objetos Java a objetos Xml
        // Inicializar la cola de requerimientos
        getSupportActionBar().hide();
        jetusuario=findViewById(R.id.etusuario);
        jetnombre=findViewById(R.id.etnombre);
        jetcorreo=findViewById(R.id.etcorreo);
        jetclave=findViewById(R.id.etclave);
        jcbactivo=findViewById(R.id.cbactivo);
        rq = Volley.newRequestQueue(this);
        sw=0;
    }

    public void Consultar(View view){
        usr=jetusuario.getText().toString();
        if (usr.isEmpty()){
            Toast.makeText(this, "Usuario requerido para la busqueda", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            url = "http://172.18.74.101:80/WebServices/consulta.php?usr="+usr;
            jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            rq.add(jrq);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        sw=1;
        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posicion 0 del arreglo....
            jetnombre.setText(jsonObject.optString("nombre"));
            jetcorreo.setText(jsonObject.optString("correo"));
            jetclave.setText(jsonObject.optString("clave"));
            if (jsonObject.optString("activo").equals("si"))
                jcbactivo.setChecked(true);
            else
                jcbactivo.setChecked(false);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Guardar(View view){
        usr=jetusuario.getText().toString();
        nombre=jetnombre.getText().toString();
        correo=jetcorreo.getText().toString();
        clave=jetclave.getText().toString();
        if (usr.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            if(sw==0)
            url = "http://172.18.74.101:80/WebServices/registrocorreo.php";
            else{
                url="http://172.18.74.101:80/WebServices/actualiza.php";
                sw=0;
            }

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_campos();
                            Toast.makeText(getApplicationContext(), "Registro de usuario realizado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error de registro de usuario!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("usr",jetusuario.getText().toString().trim());
                    params.put("nombre", jetnombre.getText().toString().trim());
                    params.put("correo",jetcorreo.getText().toString().trim());
                    params.put("clave",jetclave.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }

    public void eliminar(View view){
        usr=jetusuario.getText().toString();
        if (usr.isEmpty()) {
            Toast.makeText(this, "El usuario es requerido", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            url = "http://172.18.74.101:80/WebServices/elimina.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_campos();
                            Toast.makeText(getApplicationContext(), "Registro eliminado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error eliminando!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("usr",jetusuario.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }

    public void anular(View view){
        usr=jetusuario.getText().toString();
        if (usr.isEmpty()) {
            Toast.makeText(this, "El usuario es requerido", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            url = "http://172.18.74.101:80/WebServices/anula.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_campos();
                            Toast.makeText(getApplicationContext(), "Registro anulado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error anulando!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("usr",jetusuario.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }

    public void Regresar(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }

    public void Limpiar(View view){
        Limpiar_campos();
    }

    private void Limpiar_campos(){
        jetcorreo.setText("");
        jetnombre.setText("");
        jetusuario.setText("");
        jetclave.setText("");
        jcbactivo.setChecked(false);
        jetcorreo.requestFocus();
        sw=0;
    }
}
