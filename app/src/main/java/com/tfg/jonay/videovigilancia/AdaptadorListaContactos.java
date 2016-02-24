package com.tfg.jonay.videovigilancia;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jonay on 23/02/16.
 */
public class AdaptadorListaContactos extends ArrayAdapter<Contacto> {

    private Activity contexto;
    private ArrayList<Contacto> datos;

    public AdaptadorListaContactos(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AdaptadorListaContactos(Activity context, int resource, ArrayList<Contacto> items) {
        super(context, resource, items);

        this.contexto = context;
        this.datos = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = contexto.getLayoutInflater();
        View item = inflater.inflate(R.layout.contacto_layout, null);

        TextView nombre = (TextView) item.findViewById(R.id.contacto_nombre);
        nombre.setText(datos.get(position).getNombre());
        TextView numero = (TextView) item.findViewById(R.id.contacto_numero);
        numero.setText("(" + datos.get(position).getNumero() + ")");
        ImageView img = (ImageView) item.findViewById(R.id.contacto_img);
        if(datos.get(position).getEstado()){
            img.setImageResource(R.drawable.si);
        }else{
            img.setImageResource(R.drawable.no);
        }

        return item;
    }

}
