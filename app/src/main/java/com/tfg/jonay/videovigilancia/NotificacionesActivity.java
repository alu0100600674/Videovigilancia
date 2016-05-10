package com.tfg.jonay.videovigilancia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NotificacionesActivity extends AppCompatActivity {

    private Notificaciones notif;
    private ListView lista;
    private GlobalClass globales;
    private BaseDeDatos app_data;
    private AdaptadorListaContactos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        setTitle("Configurar notificaciones");

        globales = (GlobalClass) getApplicationContext();
        notif = globales.getNotificaciones();
        app_data = globales.getBaseDeDatos();

        adaptador = new AdaptadorListaContactos(this, android.R.layout.simple_list_item_1, notif.getDestinatarios());

        lista = (ListView) findViewById(R.id.lista_destinatarios);
        TextView titulo = new TextView(getApplicationContext());
        titulo.setText("Destinatarios:");
        lista.addHeaderView(titulo);
        lista.setAdapter(adaptador);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Contacto cont = (Contacto) lista.getItemAtPosition(position);
                String des_hab = "";
                if (cont.getEstado()) {
                    des_hab = "Deshabilitar";
                } else {
                    des_hab = "Habilitar";
                }

                String[] opc = new String[]{"Eliminar", des_hab, "Cambiar nombre"};
                AlertDialog opciones = new AlertDialog.Builder(NotificacionesActivity.this)
//                        .setTitle("Opciones")
                        .setItems(opc,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int selected) {
                                        if (selected == 0) { // Eliminar
                                            Contacto con = (Contacto) lista.getItemAtPosition(position);
                                            eliminar(con);
                                        } else if (selected == 1) { // Des/habilitar
                                            Contacto con = (Contacto) lista.getItemAtPosition(position);
                                            deshabilitar(con);
                                        } else if (selected == 2){ // Cambiar nombre
                                            Contacto con = (Contacto) lista.getItemAtPosition(position);
                                            cambiarNombre(con);
                                        }
                                    }
                                }).create();
                opciones.show();

                return true;
            }
        });

        Button btn_add_destino = (Button) findViewById(R.id.btn_add_destinatario);
        btn_add_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_contactos = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent_contactos, 1);
            }
        });

        Button btn_add_numero = (Button) findViewById(R.id.btn_add_numero);
        btn_add_numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text_num = (EditText) findViewById(R.id.text_intr_telefono);
                if(!text_num.getText().toString().matches("")){
                    addNumero(text_num.getText().toString());
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case(1):
                if(resultCode == Activity.RESULT_OK){
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    String nombre = null;
                    String id = null;
                    if(c.moveToFirst()){
                        id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        nombre = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    }
                    String numero = null;
                    if(nombre != null){
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                        while(phones.moveToNext()){
                            numero = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        phones.close();
                    }
                    c.close();
                    if(numero != null){
                        final String numero2 = numero;
                        final String nombre2 = nombre;

                        AlertDialog.Builder add_dialogo = new AlertDialog.Builder(this);
                        add_dialogo.setTitle("Añadir destinatario");
                        add_dialogo.setMessage("¿Añadir el destinatario " + nombre2 + " (" + numero + ")?");
                        add_dialogo.setCancelable(false);

                        DialogInterface.OnClickListener click_ok = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(notif.addDestinatario(new Contacto(nombre2, numero2))){
                                    app_data.addDestinatario(nombre2, numero2);
                                    Toast.makeText(NotificacionesActivity.this, nombre2 + " (" + numero2 + ") añadido!", Toast.LENGTH_SHORT).show();
                                    adaptador.notifyDataSetChanged();
                                }
                            }
                        };

                        add_dialogo.setPositiveButton("Confirmar", click_ok);
                        add_dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface add_dialogo, int id) {
                                Toast.makeText(NotificacionesActivity.this, "Operación cancelada!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        add_dialogo.show();
                    }
                }
                break;
        }
    }

    private void addNumero(String numero){
        final String numero2 = numero;

        AlertDialog.Builder add_dialogo = new AlertDialog.Builder(this);
        add_dialogo.setTitle("Añadir destinatario");
        add_dialogo.setMessage("¿Añadir el destinatario " + numero + "?");
        add_dialogo.setCancelable(false);

        DialogInterface.OnClickListener click_ok = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(notif.addDestinatario(new Contacto(numero2))){
                    app_data.addDestinatario(numero2);
                    Toast.makeText(NotificacionesActivity.this, numero2 + " añadido!", Toast.LENGTH_SHORT).show();
                    adaptador.notifyDataSetChanged();
                }
            }
        };

        add_dialogo.setPositiveButton("Confirmar", click_ok);
        add_dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface add_dialogo, int id) {
                Toast.makeText(NotificacionesActivity.this, "Operación cancelada!", Toast.LENGTH_SHORT).show();
            }
        });
        add_dialogo.show();
    }

    private void eliminar(Contacto contacto){
        final String numero2 = contacto.getNumero();
        final String nombre2 = contacto.getNombre();
        AlertDialog.Builder add_dialogo = new AlertDialog.Builder(this);
        add_dialogo.setTitle("Eliminar destinatario");
        add_dialogo.setMessage("¿Eliminar el destinatario " + nombre2 + " (" + numero2 + ")?");
        add_dialogo.setCancelable(false);

        DialogInterface.OnClickListener click_ok = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (notif.delDestinatario(new Contacto(numero2))) {
                    app_data.delDestinatario(numero2);
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(NotificacionesActivity.this, nombre2 + " (" + numero2 + ") eliminado!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        add_dialogo.setPositiveButton("Confirmar", click_ok);
        add_dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface add_dialogo, int id) {
                Toast.makeText(NotificacionesActivity.this, "Operación cancelada!", Toast.LENGTH_SHORT).show();
            }
        });
        add_dialogo.show();
    }

    private void deshabilitar(Contacto contacto){
        final String numero2 = contacto.getNumero();
        final String nombre2 = contacto.getNombre();
        final boolean estado2 = contacto.getEstado();

        AlertDialog.Builder deshabilitar_dialogo = new AlertDialog.Builder(this);
        if(estado2){
            deshabilitar_dialogo.setTitle("Deshabilitar destinatario");
            deshabilitar_dialogo.setMessage("¿Deshabilitar el destinatario " + nombre2 + " (" + numero2 + ")?");
        }else{
            deshabilitar_dialogo.setTitle("Habilitar destinatario");
            deshabilitar_dialogo.setMessage("¿Habilitar el destinatario " + nombre2 + " (" + numero2 + ")?");
        }
        deshabilitar_dialogo.setCancelable(false);

        DialogInterface.OnClickListener click_ok = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (notif.cambiarEstado(new Contacto(nombre2, numero2, estado2))) {
                    int est = estado2 ? 0 : 1;
                    app_data.updateDestinatarioEstado(numero2, est);
                    adaptador.notifyDataSetChanged();
                    if(estado2){
                        Toast.makeText(NotificacionesActivity.this, nombre2 + " (" + numero2 + ") deshabilitado!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NotificacionesActivity.this, nombre2 + " (" + numero2 + ") habilitado!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };

        deshabilitar_dialogo.setPositiveButton("Confirmar", click_ok);
        deshabilitar_dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface deshabilitar_dialogo, int id) {
                Toast.makeText(NotificacionesActivity.this, "Operación cancelada!", Toast.LENGTH_SHORT).show();
            }
        });
        deshabilitar_dialogo.show();
    }

    private void cambiarNombre(final Contacto contacto){
        String nombre2 = contacto.getNombre();
        final String numero2 = contacto.getNumero();

        AlertDialog.Builder cambiar_nombre_dialogo = new AlertDialog.Builder(this);
        cambiar_nombre_dialogo.setTitle("Cambiar nombre");
        cambiar_nombre_dialogo.setMessage("Cambiar el nombre para " + nombre2 + " (" + numero2 + ").");
        cambiar_nombre_dialogo.setCancelable(false);

        final EditText nuevo_nombre = new EditText(this);
        nuevo_nombre.setInputType(InputType.TYPE_CLASS_TEXT);
        nuevo_nombre.setText(nombre2);
        cambiar_nombre_dialogo.setView(nuevo_nombre);

        cambiar_nombre_dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface cambiar_nombre_dialogo, int id) {
                Toast.makeText(NotificacionesActivity.this, "Operación cancelada!", Toast.LENGTH_SHORT).show();
            }
        });

        cambiar_nombre_dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface cambiar_nombre_dialogo, int id) {
                String nombre_nuevo = nuevo_nombre.getText().toString();
                if(notif.cambiarNombre(contacto, nuevo_nombre.getText().toString())){
                    app_data.updateDestinatarioNombre(numero2, nombre_nuevo);
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(NotificacionesActivity.this, "Destinatario renombrado", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cambiar_nombre_dialogo.show();
    }

}
