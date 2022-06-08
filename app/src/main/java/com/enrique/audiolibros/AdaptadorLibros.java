package com.enrique.audiolibros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {


    /*
    El método setOnItemClickListener no viene para el ReciclerView, por lo que hay que asignar
    un escuchador de forma independiente para cada una de las vistas. Creamos nuestro propio
    método setOnItemClickListener.
     */
    private View.OnClickListener onClickListener;//Campo
    private View.OnLongClickListener onLongClickListener;

    private LayoutInflater inflador;//Crea Layouts a partir del XML
    protected Vector<Libro> vectorLibros;//Vector con libros a visualizar private
    Context contexto;

    public AdaptadorLibros(Context contexto, Vector<Libro> vectorLibros) {
        inflador = (LayoutInflater) contexto .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorLibros = vectorLibros;
        this.contexto = contexto;
    }
    //Éste método funcionará a modo de setter
    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    //Éste método funcionará a modo de setter PARA EL CLICK LARGO
    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            portada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
        } //Constructor
    }//class ViewHolder


    @Override
    public AdaptadorLibros.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_selector, null);
        /*
        La siguiente línea aplica el escuchador setOnItemClickListener de arriba a cada una
        de las vistas creadas.
         */
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Libro libro = vectorLibros.elementAt(posicion);
        holder.portada.setImageResource(libro.recursoImagen);
        holder.titulo.setText(libro.titulo);
    }
    // Indicamos el número de elementos de la lista
    @Override
    public int getItemCount() {
        return vectorLibros.size();
    }
}//Clase