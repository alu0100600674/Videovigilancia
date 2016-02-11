package com.tfg.jonay.videovigilancia;

import android.telephony.SmsManager;
import android.text.format.Time;

/**
 * Created by jonay on 11/02/16.
 */
public class Notificaciones {

    private String numero = "628435740";
    private String numero2 = "636949909";
    private SmsManager sms_manager;

    public Notificaciones(){
        sms_manager = SmsManager.getDefault();
//        sms_manager.sendTextMessage(numero, null, mensaje_movimiento, null, null);
//        System.out.println("Mensaje enviado... a " + numero);
    }

    public void enviarSmsMovimiento(){
        Time now = new Time(Time.getCurrentTimezone());
        now.setToNow();
        String mensaje = "Movimiento detectado el día " + now.monthDay + "/" + now.month + "/" + now.year + " a las " + now.format("%k:%M:%S") + ".";
        sms_manager.sendTextMessage(numero, null, mensaje, null, null);
    }

}
