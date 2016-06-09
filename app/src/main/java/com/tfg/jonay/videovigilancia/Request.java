package com.tfg.jonay.videovigilancia;

import android.content.Context;
import android.util.Log;
import org.json.JSONObject;
import java.util.HashMap;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


/**
 * Created by jonay on 9/04/16.
 */
public class Request {

    /* new User con datos sin cifrar */
//    //Función que registra a un usuario en el servicio web la primera vez que usa la app
//    public static void newUser(String MAC, RequestQueue requestQueue, String stream_short_url, String web_url, String ip_actual) {
//
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("name", MAC);
//        params.put("server", stream_short_url);
//        params.put("ipcamara", ip_actual);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, web_url + "/camara", new JSONObject(params),
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Volley nuevoUsuario Request ", response.toString());
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Volley nuevoUsuario Request Error ", error.toString());
//                    }
//                });
//
//        requestQueue.add(jsonObjectRequest);
//    }

    /* new User con datos cifrados */
    //Función que registra a un usuario en el servicio web la primera vez que usa la app
    public static void newUser(String MAC, RequestQueue requestQueue, String stream_short_url, String web_url, String ip_actual) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", AES.cifrar(MAC));
        params.put("server", AES.cifrar(stream_short_url));
        params.put("ipcamara", AES.cifrar(ip_actual));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, web_url + "/camara", new JSONObject(params),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("nUsuario Request ", response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("nUsuario Request Error ", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public static void newUser(Context ctx, String MAC, RequestQueue requestQueue, String stream_short_url, String web_url, String ip_actual) {
        GlobalClass globales = (GlobalClass) ctx.getApplicationContext();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", AES.cifrar(MAC, globales.getClaveCompartida()));
        params.put("server", AES.cifrar(stream_short_url, globales.getClaveCompartida()));
        params.put("ipcamara", AES.cifrar(ip_actual, globales.getClaveCompartida()));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, web_url + "/camara", new JSONObject(params),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("nUsuario Request ", response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("nUsuario Request Error ", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }



    //Función que establece un video como online
//    public static void streamOnline(String MAC, RequestQueue requestQueue, String web_url) {
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PUT, web_url + "/online/" + MAC,
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Volley streamOnline Request ", response.toString());
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Volley streamOnline Request Error ", error.toString());
//                    }
//                });
//
//        requestQueue.add(jsonObjectRequest);
//    }

    /* streamOnline con datos sin cifrar */
    //Función que establece un video como online
//    public static void streamOnline(String MAC, RequestQueue requestQueue, String web_url, String ip_actual, String stream_short_url) {
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("name", MAC);
//        params.put("ipcamara", ip_actual);
//        params.put("server", stream_short_url);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PUT, web_url + "/online/" + MAC, new JSONObject(params),
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Volley streamOnline Request ", response.toString());
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Volley streamOnline Request Error ", error.toString());
//                    }
//                });
//
//        requestQueue.add(jsonObjectRequest);
//    }

    /* streamOnline con datos cifrados */
    //Función que establece un video como online
    public static void streamOnline(String MAC, RequestQueue requestQueue, String web_url, String ip_actual, String stream_short_url) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", AES.cifrar(MAC));
        params.put("ipcamara", AES.cifrar(ip_actual));
        params.put("server", AES.cifrar(stream_short_url));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PUT, web_url + "/online/" + MAC, new JSONObject(params),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Online Request ", response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Online Request Error ", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public static void streamOnline(Context ctx, String MAC, RequestQueue requestQueue, String web_url, String ip_actual, String stream_short_url) {
        GlobalClass globales = (GlobalClass) ctx.getApplicationContext();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", AES.cifrar(MAC, globales.getClaveCompartida()));
        params.put("ipcamara", AES.cifrar(ip_actual, globales.getClaveCompartida()));
        params.put("server", AES.cifrar(stream_short_url, globales.getClaveCompartida()));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PUT, web_url + "/online/" + MAC, new JSONObject(params),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Online Request ", response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Online Request Error ", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    /* streamOffline con datos sin cifrar */
//    //Función que establece un video como offline
//    public static void streamOffline(String MAC, RequestQueue requestQueue, String web_url) {
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PUT, web_url + "/offline/" + MAC,
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Volley streamOffline Request ", response.toString());
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Volley streamOffline Request Error ", error.toString());
//                    }
//                });
//
//        requestQueue.add(jsonObjectRequest);
//    }

    /* streamOffline con datos cifrados */
    //Función que establece un video como offline
    public static void streamOffline(String MAC, RequestQueue requestQueue, String web_url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PUT, web_url + "/offline/" + MAC,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Offline Request ", response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Offline Request Error ", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
