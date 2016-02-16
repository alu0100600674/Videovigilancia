package com.tfg.jonay.videovigilancia;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jonay on 16/02/16.
 */
public class BaseDeDatos {
    public static SQLiteDatabase db;

    public BaseDeDatos(String bd){
        db = SQLiteDatabase.openOrCreateDatabase(bd, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS destinatarios(nombre VARCHAR, numero VARCHAR);");
    }

    public void addDestinatario(String nombre, String numero){
        db.execSQL("INSERT INTO destinatarios VALUES('" + nombre + "', '" + numero +"');");
    }
}
