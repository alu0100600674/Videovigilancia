package com.tfg.jonay.videovigilancia;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import com.jwetherell.motiondetection.detection.IMotionDetection;
import com.jwetherell.motiondetection.detection.RgbMotionDetection;
import com.jwetherell.motiondetection.image.ImageProcessing;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private Button btn_video;
    private Button btn_flash;
//    private Button btn_movimiento;
//    private Button btn_streaming;
//    private Button btn_mov;
    private Button btn_stop;
    private net.majorkernelpanic.streaming.gl.SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera cammov;
//    private Camera.Parameters p;
    private boolean encendida;
    private boolean flash;

    private GlobalClass globales;
    private Notificaciones notif;
    private Servidor serv;

    private Thread th_mov;

    private boolean emitiendo = false;
    private boolean camRelease = true;
    private int contadorFrame = 0;

    private boolean ActivityActivaBt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        setTitle(R.string.ver_video);

        globales = (GlobalClass) getApplicationContext();
        notif = globales.getNotificaciones();
        serv = globales.getServidor();

        globales.ini_camAct(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        btn_video = (Button) findViewById(R.id.ver_video);
        btn_flash = (Button) findViewById(R.id.btn_flash);
//        btn_movimiento = (Button) findViewById(R.id.btn_simular_mov);
//        btn_streaming = (Button) findViewById(R.id.btn_streaming);
//        btn_mov = (Button) findViewById(R.id.btn_mov);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_flash.setTypeface(font);
        btn_video.setTypeface(font);
//        btn_movimiento.setTypeface(font);
//        btn_streaming.setTypeface(font);
//        btn_mov.setTypeface(font);
        btn_stop.setTypeface(font);
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


//        th_mov = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Camera cammov = Camera.open();
////                cammov.setDisplayOrientation(90);
//
//                Camera.Parameters param = cammov.getParameters();
//                param.setPreviewFrameRate(30);
//                param.setPreviewFpsRange(15000, 30000);
//                cammov.setParameters(param);
//                try {
//                    cammov.setPreviewDisplay(surfaceView.getHolder());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                cammov.addCallbackBuffer(new byte[3110400]);
//                cammov.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
//                    @Override
//                    public void onPreviewFrame(byte[] data, Camera camera) {
//                        cammov.addCallbackBuffer(data);
//                        int[] rgb = ImageProcessing.decodeYUV420SPtoRGB(data, 1152, 648);
//                        IMotionDetection detector = new RgbMotionDetection();
//                        boolean detected = detector.detect(rgb, 1152, 648);
//                        System.out.println("----->Bool:  " + detected);
//
//                        if(detected){
//                            cammov.stopPreview();
//                            cammov.release();
//                            try {
//                                th_mov.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                cammov.startPreview();
//            }
//        });
//        th_mov.start();


//        btn_movimiento.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notif.enviarSmsMovimiento();
//            }
//        });

//        btn_streaming.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    startCamaraStreaming();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        btn_mov.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startCamaraMovimiento();
//            }
//        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Parar streaming
                serv.parar();
                Request.streamOffline(serv.getMacAddress(), serv.getRequestQueue(), serv.getWebURL());

                // Parar detección de movimiento
                if(!camRelease){
                    cammov.release();
                }

                emitiendo = false;
                camRelease = true;

                // Cambiar color del botón a rojo
                btn_video.setBackgroundColor(0xAAFF0000);
                encendida = false;

                btn_flash.setBackgroundColor(0xAAFF0000);
                flash = false;
            }
        });

//        globales.getRobot().conectar();
//        globales.getRobot().conectar2(globales.getRobotElegido());

        final BluetoothAdapter bt_adapter = BluetoothAdapter.getDefaultAdapter();

        if(bt_adapter == null){ // Si bt_adapter es null, el dispositivo no tiene bluetooth.

        }else{
            if(!bt_adapter.isEnabled()){ // Si el bluetooth esta desactivado, pedimos la activación al usuario.
//                Intent bt_enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(bt_enable_intent, 1);

                AlertDialog.Builder bt_dialog = new AlertDialog.Builder(this);
                bt_dialog.setTitle(R.string.activar_blu);
                bt_dialog.setMessage(R.string.robot_blu);
                bt_dialog.setCancelable(false);
                bt_dialog.setPositiveButton(R.string.activar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bt_adapter.enable();
                        ActivityActivaBt = true;
                    }
                });
                bt_dialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                bt_dialog.show();
            }
        }

        globales.getRobotSocket().abrirSocket();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serv.parar();
//        cammov.stopPreview();
        if(!camRelease){
            cammov.release();
        }
        if(ActivityActivaBt){
            BluetoothAdapter bt_adapter = BluetoothAdapter.getDefaultAdapter();
            bt_adapter.disable();
        }
//        globales.getRobotSocket().cerrarSocket();
//        globales.getRobotSocket().cerrarSocket2();
        Request.streamOffline(serv.getMacAddress(), serv.getRequestQueue(), serv.getWebURL());
    }

    /* Solo Streaming */
    public void startCamaraStreaming() throws IOException{
        startStreaming();
    }

    /* Solo Detección de Movimiento */
    public void startCamaraMovimiento(){
        cammov = Camera.open();
        cammov.setDisplayOrientation(90);

        Camera.Parameters param = cammov.getParameters();
        param.setPreviewFrameRate(30);
        param.setPreviewFpsRange(15000, 30000);
        cammov.setParameters(param);
        try {
            cammov.setPreviewDisplay(surfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cammov.addCallbackBuffer(new byte[3110400]);
        contadorFrame = 0;
        cammov.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                contadorFrame++;

                cammov.addCallbackBuffer(data);
                int[] rgb = ImageProcessing.decodeYUV420SPtoRGB(data, 1152, 648);
                IMotionDetection detector = new RgbMotionDetection();
                boolean detected = detector.detect(rgb, 1152, 648);
//                    System.out.println("----->Bool:  " + detected + "  " + contadorFrame);

                if (detected && contadorFrame > 1) {
//                    cammov.stopPreview();
                    cammov.release();
//                    serv.iniciarStreaming();
                    notif.enviarSmsMovimiento();
                    serv.iniciar(surfaceView, getApplicationContext());
//                    startStreaming();
                    emitiendo = true;
                    camRelease = true;
                }
                contadorFrame = 2;
            }
        });
        cammov.startPreview();
        camRelease = false;
    }

    /* Detección de movimiento y después streaming */
    public void startCamara() throws IOException{
        // Si la camara y el streaming estan descativados
        // Si la camara esta encendida
        // Si el streaming esta encendido

        System.out.println("Emitiendo: " + serv.getClient().isStreaming());

//        if(camRelease && !emitiendo && !serv.getClient().isStreaming()){
        if(camRelease && !emitiendo){
            System.out.println("Dos desactivados");
            cammov = Camera.open();
            cammov.setDisplayOrientation(90);

            Camera.Parameters param = cammov.getParameters();
            param.setPreviewFrameRate(30);
            param.setPreviewFpsRange(15000, 30000);
            cammov.setParameters(param);
            try {
                cammov.setPreviewDisplay(surfaceView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            cammov.addCallbackBuffer(new byte[3110400]);
            contadorFrame = 0;
            cammov.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    contadorFrame++;

                    cammov.addCallbackBuffer(data);
                    int[] rgb = ImageProcessing.decodeYUV420SPtoRGB(data, 1152, 648);
                    IMotionDetection detector = new RgbMotionDetection();
                    boolean detected = detector.detect(rgb, 1152, 648);
//                    System.out.println("----->Bool:  " + detected + "  " + contadorFrame);

                    if (detected && contadorFrame > 1) {
//                    cammov.stopPreview();
                        cammov.release();
//                    serv.iniciarStreaming();
                        notif.enviarSmsMovimiento();
                        serv.iniciar(surfaceView, getApplicationContext());
                        startStreaming();
                        emitiendo = true;
                        camRelease = true;
                        btn_video.setBackgroundColor(0xAA009900); // Botón verde
                    }
                    contadorFrame = 2;
                }
            });
            cammov.startPreview();
            btn_video.setBackgroundColor(0xAAFFD700); // Botón amarillo
            camRelease = false;
        }

//        else if(!emitiendo && cammov != null && !serv.getClient().isStreaming()){
//            System.out.println("camara activada y sin emitir");
//            cammov.stopPreview();
//            cammov.release();
//            camRelease = true;
//
////            cammov = null;
//        }
//
//        else if(emitiendo){
//            System.out.println("camara desactivada y emitiendo");
////            startStreaming();
//            serv.pararStreaming();
//            emitiendo = false;
//        }
//
//
//
//
//        if(!encendida){
//            btn_video.setBackgroundColor(0xAA009900);
//        }else{
//            btn_video.setBackgroundColor(0xAAFF0000);
//        }
//        encendida = !encendida;

        encendida = true;
    }

    private void startStreaming(){
//        cammov.release();
        serv.iniciarStreaming();
    }

    public void startFlash(){
        if(emitiendo && serv.getClient().isStreaming()){
            serv.iniciarFlash();
            if(!flash){
                btn_flash.setBackgroundColor(0xAA009900);
            }else{
                btn_flash.setBackgroundColor(0xAAFF0000);
            }
            flash = !flash;
        }

//        serv.iniciarFlash();
//        if(!flash){
//            btn_flash.setBackgroundColor(0xAA009900);
//        }else{
//            btn_flash.setBackgroundColor(0xAAFF0000);
//        }
//        flash = !flash;
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
