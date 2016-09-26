package com.fatec.fernanda.appredes.dao;

import com.fatec.fernanda.appredes.models.Topico;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Fernanda on 14/09/2016.
 */

public class FirebaseHelper {

    DatabaseReference db;
    ArrayList<String> conteudos = new ArrayList<>();
    ArrayList<Topico> topicos = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }


    //READ
    public ArrayList<?> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return conteudos;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String titulo = ds.getValue(String.class);
            conteudos.add(titulo);
            return;
        }
    }

    //PEGAR TOPICOS DO CONTEUDO SELECIONADO

    public ArrayList<?> getTopicosConteudo(final int idConteudo) {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getListaTopicos(dataSnapshot, idConteudo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return topicos;
    }

    private void getListaTopicos(DataSnapshot dataSnapshot, int idConteudo) {

        /*
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            DataSnapshot topicoCorrente = ds.child(String.valueOf(idConteudo));



            Topico novoTopido = ds.getValue(Topico.class);
            if (novoTopido.getIdConteudo() == idConteudo) {
                topicos.add(novoTopido);
                System.out.println("Novo topico: " + novoTopido.getTituloTopico());
            }
            */
    }

}
