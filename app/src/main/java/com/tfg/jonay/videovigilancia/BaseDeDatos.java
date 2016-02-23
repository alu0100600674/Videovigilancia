package com.tfg.jonay.videovigilancia;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by jonay on 16/02/16.
 */
public class BaseDeDatos {
    private SQLiteDatabase db;

    public BaseDeDatos(SQLiteDatabase bdd){
        db = bdd;
    }

    public void crearTablas(){
        db.execSQL("CREATE TABLE IF NOT EXISTS destinatarios(nombre VARCHAR, numero VARCHAR, activo BOOLEAN);");
    }

    public void addDestinatario(String nombre, String numero){
        db.execSQL("INSERT INTO destinatarios VALUES('" + nombre + "', '" + numero +"', 1);");
    }

    public void delDestinatario(String numero){
        db.execSQL("DELETE FROM destinatarios WHERE numero = " + numero + ";");
    }

    public void updateDestinatarioEstado(String numero, int estado){
        db.execSQL("UPDATE destinatarios SET activo = " + Integer.toString(estado) + " WHERE numero = " + numero + ";");
    }

    public ArrayList<Contacto> selectDestinatarios(){
        ArrayList<Contacto> lista_destinatarios = new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM destinatarios;", null);
        while(result.moveToNext()){
            boolean est;
            if(result.getInt(2) == 0){
                est = false;
            }else{
                est = true;
            }
            lista_destinatarios.add(new Contacto(result.getString(0), result.getString(1), est));
        }

        return lista_destinatarios;
    }
}
