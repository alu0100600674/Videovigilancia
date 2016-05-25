package com.tfg.jonay.videovigilancia;

import android.content.res.Resources;

/**
 * Created by jonay on 16/02/16.
 */
public class Contacto {

    private String nombre;
    private String numero;
    private boolean activo;

    public Contacto(String nom, String num){
        nombre = nom;
        numero = num;
        activo = true;
    }

    public Contacto(String num){
        nombre = "Sin nombre";
        numero = num;
        activo = true;
    }

    public Contacto(String nom, String num, boolean est){
        nombre = nom;
        numero = num;
        activo = est;
    }

    public String getNombre(){
        return nombre;
    }

    public String getNumero(){
        return numero;
    }

    public boolean getEstado(){
        return activo;
    }

    public void setNombre(String nom){
        nombre = nom;
    }

    public void setEstado(boolean estado){
        activo = estado;
    }
}
