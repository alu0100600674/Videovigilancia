package com.tfg.jonay.videovigilancia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ServidorActivity extends AppCompatActivity {

    private EditText input_url;
    private EditText input_user;
    private EditText input_pass;
    private Button btn_guardar;
    private CheckBox check_pass;

    private GlobalClass globales;
    private Servidor servidor;
    private BaseDeDatos app_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor);

        globales = (GlobalClass) getApplicationContext();
        app_data = globales.getBaseDeDatos();
        servidor = globales.getServidor();

        servidor.setURL(app_data.getServData()[0]);
        servidor.setUsername(app_data.getServData()[1]);
        servidor.setPassword(app_data.getServData()[2]);

        input_url = (EditText) findViewById(R.id.serv_input_url);
        input_user = (EditText) findViewById(R.id.serv_input_user);
        input_pass = (EditText) findViewById(R.id.serv_input_pass);
        btn_guardar = (Button) findViewById(R.id.btn_serv_guardar);
        check_pass = (CheckBox) findViewById(R.id.serv_ver_pass);

        // Rellenar los EditText con los datos actuales.
        input_url.setText(servidor.getURL());
        input_user.setText(servidor.getUsername());
        input_pass.setText(servidor.getPassword());

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servidor.setURL(input_url.getText().toString());
                servidor.setUsername(input_user.getText().toString());
                servidor.setPassword(input_pass.getText().toString());

                app_data.updateServidor(input_url.getText().toString(), input_user.getText().toString(), input_pass.getText().toString());
                finish();
                Toast.makeText(ServidorActivity.this, "Datos del servidor guardados!", Toast.LENGTH_SHORT).show();
            }
        });

        check_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_pass.isChecked()){ // Mostrar contrasena
                    input_pass.setTransformationMethod(null);
                }else{ // Ocultar contrasena
                    input_pass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }
}
