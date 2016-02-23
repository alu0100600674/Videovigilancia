package com.tfg.jonay.videovigilancia;

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
        nombre = "Sin Nombre";
        numero = num;
        activo = true;
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
}
