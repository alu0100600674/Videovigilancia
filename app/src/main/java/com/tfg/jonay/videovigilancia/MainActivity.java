package com.tfg.jonay.videovigilancia;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.charlie.ev3.BluetoothCommunication;
import com.charlie.ev3.Brick;
import com.charlie.ev3.OutputPort;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private GlobalClass globales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.mipmap.ic_launcher2);

        /* Pruebas */
//        String cif = Cifrado.decrypt("Hola que tal");
//        System.out.println(cif);

        //RSA
//        KeyPairGenerator generator = null;
//        try {
//            generator = KeyPairGenerator.getInstance("RSA");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        generator.initialize(2048);
//        KeyPair keyPair = generator.generateKeyPair();
//
//        String cif = Cifrado.crypt("Hola que tal", keyPair.getPublic());
//        System.out.println(cif);
//
//        String descif = Cifrado.decrypt(cif, keyPair.getPrivate());
//        System.out.println(descif);

//        // AES 256
//        String msg = "hola que tal";
//        String msg_cif = null;
//        String msg_descif = null;
//
//        KeyGenerator keyGenerator = null;
//        try {
//            keyGenerator = KeyGenerator.getInstance("AES");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        keyGenerator.init(256);
//        SecretKey secretKey = keyGenerator.generateKey();
//
//
//        System.out.println(Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
//
//        Cipher cipher = null;
//        Cipher descipher = null;
//        try {
//            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            descipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        }
//        try {
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            descipher.init(Cipher.DECRYPT_MODE, secretKey);
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
//
//        // Cifrar
//        try {
//            byte[] utf8 = msg.getBytes("UTF8");
//            byte[] enc = cipher.doFinal(utf8);
//            msg_cif = Base64.encodeToString(enc, Base64.DEFAULT);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Cif: " + msg_cif);
//
////         Descifrar
//        byte[] utf8 = null;
//        byte[] dec = Base64.decode(msg_cif, Base64.DEFAULT);
//        try {
//            utf8 = descipher.doFinal(dec);
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(dec);
//        System.out.println(utf8);
////            String msg_descifa = new String(utf8, "UTF8");
////            System.out.println(msg_descifa);
//
//        System.out.println("Descif: " + msg_descif);

        /* Fin Pruebas */

        globales = (GlobalClass) getApplicationContext();
        if(globales.getNotificaciones() == null){
            globales.ini();
        }

        globales.ini_bdd();
        globales.getNotificaciones().limpiarDestinatarios();
        globales.getNotificaciones().cargarDesdeBDD(globales.getBaseDeDatos().selectDestinatarios());

        globales.ini_serv();
        globales.getServidor().cargarDesdeBDD(globales.getBaseDeDatos().getServData());
        globales.getServidor().inicializarRequestQueue(this);
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        globales.getServidor().setMacAddress(wInfo.getMacAddress());
        globales.getServidor().setIpActual(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));

        globales.ini_robot();
        globales.ini_robotSocket();
        globales.getRobotSocket().setRobot(globales.getRobot());
//        globales.getRobotSocket().abrirSocket(); // Trasladado a CameraActivity.
        globales.getRobotSocket().setServidor(globales.getServidor());

        globales.setRobotElegido(globales.getBaseDeDatos().getRobotData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        globales.getRobotSocket().cerrarSocket();
//        globales.getRobotSocket().cerrarSocket2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
