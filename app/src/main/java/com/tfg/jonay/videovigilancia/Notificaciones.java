package com.tfg.jonay.videovigilancia;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.text.format.Time;

import java.util.ArrayList;

/**
 * Created by jonay on 11/02/16.
 */
public class Notificaciones {

    private SmsManager sms_manager;
    private ArrayList<String> destinatarios;
    private String nombre_app;

    public Notificaciones(String app_name){
        nombre_app = app_name;
        sms_manager = SmsManager.getDefault();
        destinatarios = new ArrayList<>();
    }

    public boolean addDestinatario(String numero){
        String mensaje = nombre_app + ": Ha sido añadido a la lista de notificaciones.";
        boolean encontrado = false;
        for(int i = 0; i < destinatarios.size(); i++){ // Comprobar que el número no estaba ya en la lista.
            if(destinatarios.get(i).equals(numero)){
                encontrado = true;
            }
        }
        if(!encontrado){
            destinatarios.add(numero);
            sms_manager.sendTextMessage(numero, null, mensaje, null, null);
            return true;
        }
        return false;
    }

    public boolean delDestinatario(String numero){
        for(int i = 0; i < destinatarios.size(); i++){
            if(destinatarios.get(i) == numero){
                String mensaje = nombre_app + ": Ha sido eliminado de la lista de notificaciones.";
                sms_manager.sendTextMessage(numero, null, mensaje, null, null);
                destinatarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public void enviarSmsMovimiento(){
        Time now = new Time(Time.getCurrentTimezone());
        now.setToNow();
        String mensaje = nombre_app + ": Movimiento detectado el día " + now.monthDay + "/" + now.month + "/" + now.year + " a las " + now.format("%k:%M:%S") + ".";
        for(int i = 0; i < destinatarios.size(); i++){
            sms_manager.sendTextMessage(destinatarios.get(i), null, mensaje, null, null);
        }
    }

    public ArrayList<String> getDestinatarios(){
        return destinatarios;
    }

    public void cargarDesdeBDD(ArrayList<String> lista){
        for(int i = 0; i < lista.size(); i++){
            destinatarios.add(lista.get(i));
        }
    }

    public void limpiarDestinatarios(){
        destinatarios.clear();
    }

}
