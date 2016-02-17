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
        db.execSQL("CREATE TABLE IF NOT EXISTS destinatarios(nombre VARCHAR, numero VARCHAR);");
    }

    public void addDestinatario(String nombre, String numero){
        db.execSQL("INSERT INTO destinatarios VALUES('" + nombre + "', '" + numero +"');");
    }

    public void delDestinatario(String numero){
        db.execSQL("DELETE FROM destinatarios WHERE numero = " + numero + ";");
    }

    public ArrayList<String> selectDestinatarios(){
        ArrayList<String> lista_destinatarios = new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM destinatarios;", null);
        while(result.moveToNext()){
            lista_destinatarios.add(result.getString(1));
        }
        return lista_destinatarios;
    }
}
