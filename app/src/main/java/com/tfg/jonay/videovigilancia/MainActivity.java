package com.tfg.jonay.videovigilancia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.charlie.ev3.BluetoothCommunication;
import com.charlie.ev3.Brick;

public class MainActivity extends AppCompatActivity {

    private GlobalClass globales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        globales = (GlobalClass) getApplicationContext();
        if(globales.getNotificaciones() == null){
            globales.ini();
        }

        globales.ini_bdd();
        globales.getNotificaciones().limpiarDestinatarios();
        globales.getNotificaciones().cargarDesdeBDD(globales.getBaseDeDatos().selectDestinatarios());

        globales.ini_serv();
        globales.getServidor().cargarDesdeBDD(globales.getBaseDeDatos().getServData());



//        globales.ini_robot();

//        Brick ev3 = new Brick(new BluetoothCommunication());
//        try {
//            ev3.connect();
//            ev3.directCommand.playTone(100,(short)500,(short)1000);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }

        globales.ini_robot();

        globales.ini_robotSocket();
        globales.getRobotSocket().setRobot(globales.getRobot());
        globales.getRobotSocket().abrirSocket();
        globales.getRobotSocket().setServidor(globales.getServidor());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        globales.getRobotSocket().cerrarSocket();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
