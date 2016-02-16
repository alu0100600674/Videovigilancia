package com.tfg.jonay.videovigilancia;

import android.app.Application;

/**
 * Created by jonay on 16/02/16.
 */
public class GlobalClass extends Application{
    private Notificaciones notificaciones;

    public GlobalClass(){

    }

    public void ini(){
        notificaciones = new Notificaciones(getString(R.string.app_name));
    }

    public Notificaciones getNotificaciones(){
        return notificaciones;
    }

}
