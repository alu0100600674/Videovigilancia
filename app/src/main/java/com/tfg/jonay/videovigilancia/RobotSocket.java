package com.tfg.jonay.videovigilancia;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.google.common.base.Ascii;
import com.google.common.base.Utf8;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureSpi;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by jonay on 18/03/16.
 */
public class RobotSocket {

    private int puerto;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream DIS;
    private DataOutputStream DOS;
    private Thread threadSocket;

    private Robot robot;
    private Servidor serv;
    private GlobalClass globales;

    private BluetoothAdapter btAdapter;

    private boolean socketAbierto;

    public RobotSocket(Context ctx){
        globales = (GlobalClass) ctx.getApplicationContext();
        puerto = 1234;
        socketAbierto = true;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        crearSocket();
    }

    private void crearSocket(){
        threadSocket = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(puerto);
//                    serverSocket = new ServerSocket();
//                    serverSocket.setReuseAddress(true);
//                    serverSocket.bind(new InetSocketAddress(puerto));
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                while(true){
                while(socketAbierto){

                    if(serverSocket == null) break;

                    try {
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    try {
//                        DIS = new DataInputStream(clientSocket.getInputStream());
//                        DOS = new DataOutputStream(clientSocket.getOutputStream());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//                    if(clientSocket == null) break;

                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        int caracter = -1;
                        String msg = "";
                        while ((caracter = in.read()) != 10) {
                            msg += (char) caracter;
                        }
                        msg = AES.descifrar(msg);

                        System.out.println(msg);
                        String[] comando = msg.split("-");
                        switch (comando[0]) {
                            case "legoev3arriba":
                                if (btAdapter != null) {
                                    if (btAdapter.isEnabled()) {
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverAdelante2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                        System.out.println("arriba");
                                    }
                                }
                                break;
                            case "legoev3abajo":
                                if (btAdapter != null) {
                                    if (btAdapter.isEnabled()) {
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverAtras2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                        System.out.println("abajo");
                                    }
                                }
                                break;
                            case "legoev3izquierda":
                                if (btAdapter != null) {
                                    if (btAdapter.isEnabled()) {
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverIzquierda2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                        System.out.println("izquierda");
                                    }
                                }
                                break;
                            case "legoev3derecha":
                                if (btAdapter != null) {
                                    if (btAdapter.isEnabled()) {
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverDerecha2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                        System.out.println("derecha");
                                    }
                                }
                                break;
                            case "legoev3rotarizquierda":
                                if (btAdapter != null) {
                                    if (btAdapter.isEnabled()) {
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.rotarIzquierda2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                        System.out.println("rotar izquierda");
                                    }
                                }
                                break;
                            case "legoev3rotarderecha":
                                if (btAdapter != null) {
                                    if (btAdapter.isEnabled()) {
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.rotarDerecha2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                        System.out.println("rotar derecha");
                                    }
                                }
                                break;
                            case "robocamflash":
//                                globales.getCamAct().startFlash();
                                serv.iniciarFlash();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (clientSocket != null) {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

//                        if (DIS != null) {
//                            try {
//                                DIS.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        if (DOS != null) {
//                            try {
//                                DOS.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                }
            }
        });
    }

    public void abrirSocket(){
//        threadSocket.start();

        if(threadSocket.getState() == Thread.State.NEW){
            threadSocket.start();
        }
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

    public void cerrarSocket2(){
        socketAbierto = false;
//        try {
//            serverSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void setRobot(Robot r){
        robot = r;
    }

    public void setServidor(Servidor s){
        serv = s;
    }

}
