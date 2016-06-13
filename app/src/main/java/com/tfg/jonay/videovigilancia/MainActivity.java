package com.tfg.jonay.videovigilancia;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.charlie.ev3.BluetoothCommunication;
import com.charlie.ev3.Brick;
import com.charlie.ev3.OutputPort;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;

public class MainActivity extends AppCompatActivity {

    private GlobalClass globales;

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.mipmap.ic_launcher2);

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

        try {
            globales.ini_claves();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }


        String texto  = "holaa como estas";
        String texto2 = "holaa como estass";
        try {
            byte[] firma = Certificado.firmar(globales.getClavePrivada(), texto);

            byte[] firmanode = Crypto.base64Decode("MCQCELoYQ5tEbweviySVjGyhvIYCEKRpjlpgPl5Wm2UNSGyimyY=");

            System.out.println("Firma: " + Crypto.base64Encode(firma));
            System.out.println("Firma valida: " + Certificado.verificarFirma(globales.getClavePublica(), texto, firma));

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }


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
