package com.tfg.jonay.videovigilancia;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jonay on 18/03/16.
 */
public class RobotSocket {

    private int puerto;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream DIS;
    private Thread threadSocket;

    private Robot robot;
    private Servidor serv;
    private GlobalClass globales;

    public RobotSocket(Context ctx){
        globales = (GlobalClass) ctx.getApplicationContext();
        puerto = 1234;
        crearSocket();
    }

    private void crearSocket(){
        threadSocket = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(puerto);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    DIS = new DataInputStream(clientSocket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    while(true){
                        switch(in.readLine()){
                            case "legoev3arriba":
                                globales.getRobot().conectar2(globales.getRobotElegido());
                                robot.moverAdelante();
                                globales.getRobot().desconectar();
//                                System.out.println("arriba");
                                break;
                            case "legoev3abajo":
                                globales.getRobot().conectar2(globales.getRobotElegido());
                                robot.moverAtras();
                                globales.getRobot().desconectar();
//                                System.out.println("abajo");
                                break;
                            case "legoev3izquierda":
                                globales.getRobot().conectar2(globales.getRobotElegido());
                                robot.moverIzquierda();
                                globales.getRobot().desconectar();
//                                System.out.println("izquierda");
                                break;
                            case "legoev3derecha":
                                globales.getRobot().conectar2(globales.getRobotElegido());
                                robot.moverDerecha();
                                globales.getRobot().desconectar();
//                                System.out.println("derecha");
                                break;
                            case "legoev3rotarizquierda":
                                globales.getRobot().conectar2(globales.getRobotElegido());
                                robot.rotarIzquierda();
                                globales.getRobot().desconectar();
//                                System.out.println("rotar izquierda");
                                break;
                            case "legoev3rotarderecha":
                                globales.getRobot().conectar2(globales.getRobotElegido());
                                robot.rotarDerecha();
                                globales.getRobot().desconectar();
//                                System.out.println("rotar derecha");
                                break;
                            case "videovigilanciaflash":
//                                globales.getCamAct().startFlash();
                                serv.iniciarFlash();
                                break;
                        }
//                        System.out.println(in.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(clientSocket != null){
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(DIS != null){
                        try {
                            DIS.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }



            }
        });
    }

    public void abrirSocket(){
        threadSocket.start();
    }

    public void cerrarSocket(){
//        threadSocket.stop();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRobot(Robot r){
        robot = r;
    }

    public void setServidor(Servidor s){
        serv = s;
    }

}
