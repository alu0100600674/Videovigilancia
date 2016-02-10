package com.tfg.jonay.videovigilancia;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Policy;

public class CameraActivity extends AppCompatActivity {

    private Button btn_video;
    private Button btn_flash;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camara;
    private Camera.Parameters p;
    private boolean encendida;
    private boolean flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");


        btn_video = (Button) findViewById(R.id.ver_video);
        btn_flash = (Button) findViewById(R.id.btn_flash);
        btn_flash.setTypeface(font);
        btn_video.setTypeface(font);
        surfaceView = (SurfaceView) findViewById(R.id.view_cam);
        surfaceHolder = surfaceView.getHolder();
//        surfaceHolder.setSizeFromLayout();
        encendida = false;
        flash = false;

        camara = Camera.open();
        p = camara.getParameters();
        camara.startPreview();

        int width_cam = camara.getParameters().getPreviewSize().width;
        int height_cam = camara.getParameters().getPreviewSize().height;
        final float scale = getResources().getDisplayMetrics().density; // Para las dimensiones en dp.
        surfaceView.getLayoutParams().width = (int) (height_cam / 5 * scale);
        surfaceView.getLayoutParams().height = (int) (width_cam / 5 * scale);


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

//        surfaceView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                camara.
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Parar la cámara al cerrar el Activity.
        try {
            camara.stopPreview();
            camara.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startCamara() throws IOException{
        if(!encendida){
//            camara = Camera.open();


            camara.setDisplayOrientation(90);
            p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            camara.setParameters(p);
            camara.setPreviewDisplay(surfaceHolder);
            camara.startPreview();
//            btn_video.setText(R.string.parar_video);
            btn_video.setBackgroundColor(0xAA009900);
        }else{
            camara.stopPreview();
//            camara.release();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camara.setParameters(p);
//            btn_video.setText(R.string.ver_video);
//            btn_flash.setText(R.string.flash_off);
            btn_flash.setBackgroundColor(0xAAFF0000);
            btn_video.setBackgroundColor(0xAAFF0000);
            flash = false;
        }
        encendida = !encendida;
    }

    private void startFlash(){
//        Camera.Parameters p = camara.getParameters();
        if(!flash){
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//            btn_flash.setText(R.string.flash_on);
            btn_flash.setBackgroundColor(0xAA009900);
        }else{
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//            btn_flash.setText(R.string.flash_off);
            btn_flash.setBackgroundColor(0xAAFF0000);
        }
        camara.setParameters(p);
        flash = !flash;
    }

//    private boolean cameraIsOpen(){
//
//    }

}
