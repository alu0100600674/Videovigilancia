package com.tfg.jonay.videovigilancia;

import android.widget.Toast;

import com.charlie.ev3.BluetoothCommunication;
import com.charlie.ev3.Brick;
import com.charlie.ev3.OutputPort;

import java.io.IOException;

/**
 * Created by jonay on 15/03/16.
 */
public class Robot {

    private Brick ev3;

    public Robot(){
//        ev3 = new Brick(new BluetoothCommunication());
//        try {
//            ev3.connect();
//            ev3.directCommand.playTone(100,(short)500,(short)1000);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }

//        try {
//            ev3.directCommand.startMotor(OutputPort.B);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void moverAdelante(){
        System.out.println("Robot hacia adelante");
    }

    public void moverAtras(){
        System.out.println("Robot hacia atrás");
    }

    public void moverIzquierda(){
        System.out.println("Robot hacia la izquierda");
    }

    public void moverDerecha(){
        System.out.println("Robot hacia la derecha");
    }

}
