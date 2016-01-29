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
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camara;
    private boolean encendida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        boton = (Button) findViewById(R.id.ver_video);
        surfaceView = (SurfaceView) findViewById(R.id.view_cam);
        surfaceHolder = surfaceView.getHolder();
        encendida = false;

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
    }

    private void startCamara() throws IOException{
        if(!encendida){
            camara = Camera.open();
            camara.setPreviewDisplay(surfaceHolder);
            camara.startPreview();
            encendida = true;
        }else{
            camara.stopPreview();
            camara.release();
            encendida = false;
        }
    }
}
