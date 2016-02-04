package com.tfg.jonay.videovigilancia;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RobotActivity extends AppCompatActivity {

    private BluetoothAdapter bt_adapter;
    private ListView dispositivos;
    private ArrayList<BluetoothDevice> lista_devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);

        bt_adapter = BluetoothAdapter.getDefaultAdapter();

        if(bt_adapter == null){ // Si bt_adapter es null, el dispositivo no tiene bluetooth.

        }else{
            if(!bt_adapter.isEnabled()){ // Si el bluetooth esta desactivado, pedimos la activación al usuario.
                Intent bt_enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(bt_enable_intent, 1);
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Dentroooooo");

        dispositivos = (ListView) findViewById(R.id.lista_bt_devices);
        lista_devices = new ArrayList<>();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

//            while(bt_adapter.getState() != BluetoothAdapter.STATE_ON){} // Esperar mientras el bt este desconectado.

        if (bt_adapter.isDiscovering()) {
            // El Bluetooth ya está en modo discover, lo cancelamos para iniciarlo de nuevo
            bt_adapter.cancelDiscovery();
        }
        bt_adapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Listado");
        for(int i = 0; i < lista_devices.size(); i++){
//            System.out.println(lista_devices[i]);
            System.out.println(i);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Se ha encontrado un dispositivo Bluetooth. Se obtiene la información del dispositivo del intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                lista_devices.add(device); // Añadir a la lista de dispositivos encontrados.
                Toast.makeText(getApplicationContext(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_LONG).show();
            }
        }
    };
}
