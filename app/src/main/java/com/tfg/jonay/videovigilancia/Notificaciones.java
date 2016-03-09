package com.tfg.jonay.videovigilancia;

import android.telephony.SmsManager;
import android.text.format.Time;

import java.util.ArrayList;

/**
 * Created by jonay on 11/02/16.
 */
public class Notificaciones {

    private SmsManager sms_manager;
    private ArrayList<Contacto> destinatarios;
    private String nombre_app;

    public Notificaciones(String app_name){
        nombre_app = app_name;
        sms_manager = SmsManager.getDefault();
        destinatarios = new ArrayList<>();
    }

    public boolean addDestinatario(Contacto contacto){
        String mensaje = nombre_app + ": Ha sido añadido a la lista de notificaciones.";
        boolean encontrado = false;
        for(int i = 0; i < destinatarios.size(); i++){ // Comprobar que el número no estaba ya en la lista.
            if(destinatarios.get(i).getNumero().equals(contacto.getNumero())){
                encontrado = true;
            }
        }
        if(!encontrado){
            destinatarios.add(contacto);
            sms_manager.sendTextMessage(contacto.getNumero(), null, mensaje, null, null);
            return true;
        }
        return false;
    }

    public boolean delDestinatario(Contacto contacto){
        for(int i = 0; i < destinatarios.size(); i++){
            if(destinatarios.get(i).getNumero() == contacto.getNumero()){
                String mensaje = nombre_app + ": Ha sido eliminado de la lista de notificaciones.";
                sms_manager.sendTextMessage(contacto.getNumero(), null, mensaje, null, null);
                destinatarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean cambiarEstado(Contacto contacto){
        for(int i = 0; i < destinatarios.size(); i++){
            if(destinatarios.get(i).getNumero() == contacto.getNumero()){
                destinatarios.get(i).setEstado(!destinatarios.get(i).getEstado());
                return true;
            }
        }
        return false;
    }

    public boolean cambiarNombre(Contacto contacto, String nombre_nuevo){
        for(int i = 0; i < destinatarios.size(); i++){
            if(destinatarios.get(i).getNumero() == contacto.getNumero()){
                destinatarios.get(i).setNombre(nombre_nuevo);
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
            if(destinatarios.get(i).getEstado()){
                sms_manager.sendTextMessage(destinatarios.get(i).getNumero(), null, mensaje, null, null);
            }
        }
    }

    public ArrayList<Contacto> getDestinatarios(){
        return destinatarios;
    }

    public void cargarDesdeBDD(ArrayList<Contacto> lista){
        for(int i = 0; i < lista.size(); i++){
            destinatarios.add(lista.get(i));
        }
    }

    public void limpiarDestinatarios(){
        destinatarios.clear();
    }

}
