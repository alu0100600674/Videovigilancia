package com.tfg.jonay.videovigilancia;

import android.content.Context;
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

    private Button boton;
    private Button btn_flash;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camara;
    private Camera c_flash;
    private boolean encendida;
    private boolean flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        boton = (Button) findViewById(R.id.ver_video);
        btn_flash = (Button) findViewById(R.id.btn_flash);
        surfaceView = (SurfaceView) findViewById(R.id.view_cam);
        surfaceHolder = surfaceView.getHolder();
        encendida = false;
        flash = false;

        boton.setOnClickListener(new View.OnClickListener() {
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


    }

    private void startCamara() throws IOException{
        if(!encendida){
            camara = Camera.open();
            camara.setDisplayOrientation(90);
            camara.setPreviewDisplay(surfaceHolder);
            camara.startPreview();
        }else{
            camara.stopPreview();
            camara.release();
        }
        encendida = !encendida;
    }

    private void startFlash(){
        Camera.Parameters p = camara.getParameters();
        if(!flash){
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }else{
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        camara.setParameters(p);
        flash = !flash;
    }
}
