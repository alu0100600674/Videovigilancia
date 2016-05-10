package com.tfg.jonay.videovigilancia;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RobotActivity extends AppCompatActivity {

    private BluetoothAdapter bt_adapter;
    private ListView dispositivos;
    private ArrayList<BluetoothDevice> lista_devices;

    private ArrayList<String> listado;
    ArrayAdapter<String> adaptador;

    private GlobalClass globales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);

        globales = (GlobalClass) getApplicationContext();

        TextView robot_elegido = (TextView) findViewById(R.id.robot_elegido);
        robot_elegido.setText(globales.getBaseDeDatos().getRobotData());

        setTitle("Configurar Robot");

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
    protected void onResume() { // Cambiar de forma que solo se ejecute una vez.
        super.onResume();

        if(bt_adapter.isEnabled()){
            dispositivos = (ListView) findViewById(R.id.lista_bt_devices);
            lista_devices = new ArrayList<>();

            listado = new ArrayList<>();
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listado);
            dispositivos.setAdapter(adaptador);

            dispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    globales.setRobotElegido(dispositivos.getItemAtPosition(position).toString());
                    globales.getBaseDeDatos().updateRobot(dispositivos.getItemAtPosition(position).toString());
                    adaptador.notifyDataSetChanged();
                    finish();
                }
            });

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);

            if (bt_adapter.isDiscovering()) {
                // El Bluetooth ya está en modo discover, lo cancelamos para iniciarlo de nuevo
                bt_adapter.cancelDiscovery();
            }
            bt_adapter.startDiscovery();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Listado:");
        for(int i = 0; i < lista_devices.size(); i++){
            System.out.println(lista_devices.get(i).getName());
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
//                Toast.makeText(getApplicationContext(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_LONG).show();
                listado.add(device.getName());
                adaptador.notifyDataSetChanged();
            }
        }
    };
}
