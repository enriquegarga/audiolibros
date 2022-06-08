package com.enrique.audiolibros.fragmentos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
//import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Vector;

import com.enrique.audiolibros.AdaptadorLibros;
import com.enrique.audiolibros.Aplicacion;
import com.enrique.audiolibros.Libro;
import com.enrique.audiolibros.MainActivity;
import com.enrique.audiolibros.R;


public class SelectorFragment extends Fragment {
    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibros adaptador;
    private Vector<Libro> vectorLibros;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad = (Activity) context;
            Aplicacion app = (Aplicacion) actividad.getApplication();
            adaptador = app.getAdaptador();
            vectorLibros = app.getVectorLibros();
        }//if
    }// onAttach

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_selector, container, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad,2));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(actividad, "Seleccionado el elemento: " +
                        recyclerView.getChildAdapterPosition(v), Toast.LENGTH_SHORT).show();
                ((MainActivity) actividad).mostrarDetalle( recyclerView.getChildAdapterPosition(v));
            }//onClick
        });//setOnItemClickListener

        // SE AGREGA UN ESCUCHADOR PARA EL CLICK LARGO Y MOSTRAR LAS OPCIONES PARA COMPARTIR BORRAR E INSERTAR
        adaptador.setOnItemLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = { "Compartir", "Borrar ", "Insertar" };
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) {
                            case 0: //Compartir
                                Libro libro = vectorLibros.elementAt(id);
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                startActivity(Intent.createChooser(i, "Compartir"));
                                break;
                            case 1: //Borrar.
                                Snackbar.make(v,"¿Estás seguro?", Snackbar.LENGTH_LONG).
                                        setAction("SI", new View.OnClickListener() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override public void onClick(View view) {
                                                vectorLibros.remove(id);
                                                adaptador.notifyDataSetChanged();
                                            }
                                        }).show(); //setAction
                                break;
                            case 2: //Insertar
                                vectorLibros.add(vectorLibros.elementAt(id));
                                adaptador.notifyDataSetChanged();
//     Snackbar.make(v,"Libro insertado", Snackbar.LENGTH_INDEFINITE) .show();

                                Snackbar.make(v,"Libro insertado", Snackbar.LENGTH_INDEFINITE).setAction
                                        ("OK", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    } //onClick
                                                }//View.OnClickListener()
                                        ) .show();
                                break;
                        }//switch
                    }//onClick
                });//menu.setItems
                menu.create().show();
                return true;
            }//onLongClick
        });//adaptador.setOnItemLongClickListener
        //==========================================================================================

        return vista;
    }//onCreateView
}//Clase