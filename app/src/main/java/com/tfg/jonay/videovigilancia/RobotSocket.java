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

    public RobotSocket(Context ctx){
        globales = (GlobalClass) ctx.getApplicationContext();
        puerto = 1234;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        crearSocket();
    }

//    static String encodeString(String input) {
//        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("SHA-1");
//            byte[] inputBytes = input.getBytes();
//            byte[] hashBytes = digest.digest(inputBytes);
//            return Base64.encodeToString(hashBytes, Base64.NO_WRAP);
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("TAG_TEST", e.getMessage(), e);
//        }
//        return "";
//    }

    private String obtenerMensaje(){
        return null;
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
                    DOS = new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
//                    while(true){
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    /* Este código comentado no se usa, era para usar con websocket
                    String line = "";
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("Sec-WebSocket-Key: ")) {
                            break;
                        }
                    }

                    String[] key = line.split(": ");
                    if(key.length > 1){
                        key[1] = key[1] + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
                        key[1] = encodeString(key[1]);

                        String header = "HTTP/1.1 101 Web Socket Protocol Handshake\r\n" + "Upgrade: websocket\r\n" +
                                "Connection: Upgrade\r\n" + "Sec-WebSocket-Accept:" + key[1] + "\r\n\r\n";
                        DOS.writeUTF(header);
                    }

                    System.out.println(in.readLine());
                    System.out.println(in.readLine());
                    */

                    while(true){
                        System.out.println("while");

                        int caracter = -1;
                        String msg = "";
                        while((caracter = in.read()) != 10){
                            msg += (char) caracter;
                        }

                        System.out.println(msg);
                        String[] comando = msg.split("-");
                        switch(comando[0]){
                            case "legoev3arriba":
                                if(btAdapter != null){
                                    if(btAdapter.isEnabled()){
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverAdelante2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                System.out.println("arriba");
                                    }
                                }
                                break;
                            case "legoev3abajo":
                                if(btAdapter != null){
                                    if(btAdapter.isEnabled()){
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverAtras2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                System.out.println("abajo");
                                    }
                                }
                                break;
                            case "legoev3izquierda":
                                if(btAdapter != null){
                                    if(btAdapter.isEnabled()){
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverIzquierda2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                System.out.println("izquierda");
                                    }
                                }
                                break;
                            case "legoev3derecha":
                                if(btAdapter != null){
                                    if(btAdapter.isEnabled()){
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.moverDerecha2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                System.out.println("derecha");
                                    }
                                }
                                break;
                            case "legoev3rotarizquierda":
                                if(btAdapter != null){
                                    if(btAdapter.isEnabled()){
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.rotarIzquierda2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                System.out.println("rotar izquierda");
                                    }
                                }
                                break;
                            case "legoev3rotarderecha":
                                if(btAdapter != null){
                                    if(btAdapter.isEnabled()){
                                        globales.getRobot().conectar2(globales.getRobotElegido());
                                        robot.rotarDerecha2(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
                                        globales.getRobot().desconectar();
//                                System.out.println("rotar derecha");
                                    }
                                }
                                break;
                            case "robocamflash":
//                                globales.getCamAct().startFlash();
                                serv.iniciarFlash();
                                break;
                        }
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

                    if(DOS != null){
                        try {
                            DOS.close();
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
