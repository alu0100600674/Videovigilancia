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
import android.widget.Toast;

public class RobotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);

        BluetoothAdapter bt_adapter = BluetoothAdapter.getDefaultAdapter();

        if(bt_adapter == null){ // Si bt_adapter es null, el dispositivo no tiene bluetooth.

        }

        if(!bt_adapter.isEnabled()){ // Si el bluetooth esta desactivado, pedimos la activación al usuario.
            Intent bt_enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bt_enable_intent, 1);
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        if (bt_adapter.isDiscovering()) {
            // El Bluetooth ya está en modo discover, lo cancelamos para iniciarlo de nuevo
            bt_adapter.cancelDiscovery();
        }
        bt_adapter.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Se ha encontrado un dispositivo Bluetooth
                // Se obtiene la información del dispositivo del intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                Log.i(TAG, "Dispositivo encontrado: " + device.getName() + "; MAC " + device.getAddress());
                System.out.println("Dispositivo encontrado: " + device.getName() + "; MAC " + device.getAddress());
            }
        }
    };
}
