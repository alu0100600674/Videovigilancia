package com.tfg.jonay.videovigilancia;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jonay on 16/02/16.
 */
public class GlobalClass extends Application{
    private Notificaciones notificaciones;
    private BaseDeDatos app_data;
    private Servidor servidor;
    private Robot robot;
    private RobotSocket robotSocket;

    private CameraActivity cam_act;

    private String robot_elegido;

    public GlobalClass(){

    }

    public void ini(){
        notificaciones = new Notificaciones(getString(R.string.app_name));
        notificaciones.setContext(getApplicationContext());
    }

    public void ini_bdd(){
        SQLiteDatabase bdd = openOrCreateDatabase("basededatos", MODE_PRIVATE, null);
        app_data = new BaseDeDatos(bdd);
        app_data.crearTablas();
    }

    public void ini_serv(){
        servidor = new Servidor();
    }

    public void ini_robot(){
        robot = new Robot();
    }

    public void ini_robotSocket(){
        robotSocket = new RobotSocket(getApplicationContext());
    }

    public void ini_camAct(CameraActivity ca){
        cam_act = ca;
    }

    public Notificaciones getNotificaciones(){
        return notificaciones;
    }

    public BaseDeDatos getBaseDeDatos(){
        return app_data;
    }

    public Servidor getServidor(){
        return servidor;
    }

    public Robot getRobot(){
        return robot;
    }

    public RobotSocket getRobotSocket(){
        return robotSocket;
    }

    public CameraActivity getCamAct(){
        return cam_act;
    }

    public String getRobotElegido(){
        return robot_elegido;
    }

    public void setRobotElegido(String nombre){
        robot_elegido = nombre;
    }

}
