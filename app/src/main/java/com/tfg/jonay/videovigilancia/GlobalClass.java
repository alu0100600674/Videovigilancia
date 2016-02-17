package com.tfg.jonay.videovigilancia;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jonay on 16/02/16.
 */
public class GlobalClass extends Application{
    private Notificaciones notificaciones;
    private BaseDeDatos app_data;

    public GlobalClass(){

    }

    public void ini(){
        notificaciones = new Notificaciones(getString(R.string.app_name));
    }

    public void ini_bdd(){
        SQLiteDatabase bdd = openOrCreateDatabase("basededatos", MODE_PRIVATE, null);
        app_data = new BaseDeDatos(bdd);
        app_data.crearTablas();
    }

    public Notificaciones getNotificaciones(){
        return notificaciones;
    }

    public BaseDeDatos getBaseDeDatos(){
        return app_data;
    }

}
