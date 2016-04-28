package com.tfg.jonay.videovigilancia;

import android.hardware.Camera;
import android.widget.Toast;

import com.jwetherell.motiondetection.detection.IMotionDetection;
import com.jwetherell.motiondetection.detection.RgbMotionDetection;
import com.jwetherell.motiondetection.image.ImageProcessing;

/**
 * Created by jonay on 11/04/16.
 */
public class DeteccionDeMovimiento {

    private Thread threadMov;
    private byte[] data;

    private Camera camera;

    public DeteccionDeMovimiento(byte[] dat){
        data = dat;

        threadMov = new Thread(new Runnable() {
            @Override
            public void run() {
                int[] rgb = ImageProcessing.decodeYUV420SPtoRGB(data, 1920, 1080);
                IMotionDetection detector = new RgbMotionDetection();
                boolean detected = detector.detect(rgb, 1920, 1080);
                System.out.println("----->Bool:  " + detected);
            }
        });
    }

    public void iniciarDeteccion(){
        threadMov.start();
    }

    public void pararDeteccion(){
        threadMov.interrupt();
    }
}
