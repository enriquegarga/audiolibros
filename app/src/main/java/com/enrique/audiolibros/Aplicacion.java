package com.enrique.audiolibros;

import android.app.Application;

import java.util.Vector;


public class Aplicacion extends Application {

    private Vector<Libro> vectorLibros;
    // private AdaptadorLibros adaptador;
    private AdaptadorLibrosFiltro adaptador;

    @Override
    public void onCreate() {
        super.onCreate();

        vectorLibros = Libro.ejemploLibros();
        //adaptador = new AdaptadorLibros (this, vectorLibros);
        adaptador = new AdaptadorLibrosFiltro (this, vectorLibros);
    }
    /*public AdaptadorLibros getAdaptador() {
        return adaptador;
    }*/
    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }
    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }

}