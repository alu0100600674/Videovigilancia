package com.tfg.jonay.videovigilancia;

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

    public RobotSocket(){
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
                        System.out.println(in.readLine());
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

}
