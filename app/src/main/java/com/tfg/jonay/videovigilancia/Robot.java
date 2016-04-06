package com.tfg.jonay.videovigilancia;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

//    private Thread threadRobot;
//    private BluetoothDevice btDevice;
//    private BluetoothSocket btSocket;

    public Robot(){
//        ev3 = new Brick(new BluetoothCommunication());
//        try {
//            ev3.connect();
//            ev3.directCommand.playTone(100,(short)2500,(short)1000);
//        } catch (Exception e) {
////            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//
//        try {
//            ev3.directCommand.startMotor(OutputPort.B);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void conectar(){
        ev3 = new Brick(new BluetoothCommunication());
        try {
            ev3.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moverAdelante(){
        System.out.println("Robot hacia adelante");
        try {
            ev3.directCommand.timeMotorSpeed(20, 1000, true, OutputPort.B);
            ev3.directCommand.timeMotorSpeed(20, 1000, true, OutputPort.C);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moverAtras(){
        System.out.println("Robot hacia atrás");
        try {
            ev3.directCommand.timeMotorSpeed(-20, 1000, true, OutputPort.B);
            ev3.directCommand.timeMotorSpeed(-20, 1000, true, OutputPort.C);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moverIzquierda(){
        System.out.println("Robot hacia la izquierda");
        try {
            ev3.directCommand.timeMotorSpeed(20, 1000, true, OutputPort.C);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moverDerecha(){
        System.out.println("Robot hacia la derecha");
        try {
            ev3.directCommand.timeMotorSpeed(20, 1000, true, OutputPort.B);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
