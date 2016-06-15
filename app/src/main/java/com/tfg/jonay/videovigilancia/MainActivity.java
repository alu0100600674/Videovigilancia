package com.tfg.jonay.videovigilancia;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
