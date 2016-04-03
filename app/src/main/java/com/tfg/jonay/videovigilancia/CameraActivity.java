package com.tfg.jonay.videovigilancia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private Button btn_video;
    private Button btn_flash;
    private Button btn_movimiento;
    private net.majorkernelpanic.streaming.gl.SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
//    private Camera camara;
//    private Camera.Parameters p;
    private boolean encendida;
    private boolean flash;

    private GlobalClass globales;
    private Notificaciones notif;
    private Servidor serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        globales = (GlobalClass) getApplicationContext();
        notif = globales.getNotificaciones();
        serv = globales.getServidor();

        globales.ini_camAct(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        btn_video = (Button) findViewById(R.id.ver_video);
        btn_flash = (Button) findViewById(R.id.btn_flash);
        btn_movimiento = (Button) findViewById(R.id.btn_simular_mov);
        btn_flash.setTypeface(font);
        btn_video.setTypeface(font);
        btn_movimiento.setTypeface(font);
        surfaceView = (net.majorkernelpanic.streaming.gl.SurfaceView) findViewById(R.id.view_cam);
        surfaceHolder = surfaceView.getHolder();
        encendida = false;
        flash = false;

        serv.iniciar(surfaceView, getApplicationContext());


        /* Antes, tamaño del surfaceView según la cámara */
//        camara = Camera.open();
//        p = camara.getParameters();
//        camara.startPreview();
//        int width_cam = camara.getParameters().getPreviewSize().width;
//        int height_cam = camara.getParameters().getPreviewSize().height;
//        final float scale = getResources().getDisplayMetrics().density; // Para las dimensiones en dp.
//        surfaceView.getLayoutParams().width = (int) (height_cam / 5 * scale);
//        surfaceView.getLayoutParams().height = (int) (width_cam / 5 * scale);

        /* Ahora, tamaño del surfaceView fijo */
        surfaceView.getLayoutParams().width = (int) 648;
        surfaceView.getLayoutParams().height = (int) 1152;


        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startCamara();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlash();
            }
        });
        btn_flash.setBackgroundColor(0xAAFF0000);
        btn_video.setBackgroundColor(0xAAFF0000);

        btn_movimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notif.enviarSmsMovimiento();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serv.parar();
    }

    public void startCamara() throws IOException{
        serv.iniciarStreaming();
        if(!encendida){
            btn_video.setBackgroundColor(0xAA009900);
        }else{
            btn_video.setBackgroundColor(0xAAFF0000);
        }
        encendida = !encendida;
    }

    public void startFlash(){
        serv.iniciarFlash();
        if(!flash){
            btn_flash.setBackgroundColor(0xAA009900);
        }else{
            btn_flash.setBackgroundColor(0xAAFF0000);
        }
        flash = !flash;
    }

    public void colorBtnFlash(){
        if(!flash){
            btn_flash.setBackgroundColor(0xAA009900);
        }else{
            btn_flash.setBackgroundColor(0xAAFF0000);
        }
        flash = !flash;
    }

}
