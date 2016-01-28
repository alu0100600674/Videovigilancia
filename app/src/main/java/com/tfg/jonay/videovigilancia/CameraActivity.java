package com.tfg.jonay.videovigilancia;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.security.Policy;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Camera cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();

        long start = System.currentTimeMillis();
        long end = start + 3*1000;
        while (System.currentTimeMillis() < end){}

        cam.stopPreview();
        cam.release();
    }
}
