package com.fatec.fernanda.appredes.dao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Fernanda on 14/09/2016.
 */

public class FirebaseHelper {

    DatabaseReference db;
    ArrayList<String> conteudos = new ArrayList<>();
    ArrayList<String> topicos = new ArrayList<>();

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

    //READ TOPICOS DO CONTEUDO
    public ArrayList<?> getTopicos() {

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setArrayTopicos(dataSnapshot);
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
                Log.i("OnCancelled", "Erro Child Event Listener");

            }
        });

        return topicos;
    }

    public void setArrayTopicos(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            topicos.add(ds.getKey().toString());
        }

        if(!dataSnapshot.hasChildren()){
            Log.i("VAZIO", "datasnapshot não possui filhos");
        }
    }

    public void cadastrarUsuario(final String emailUsuario){
        final int[] numUsuario = new int[1];

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numUsuario[0] = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference novoUsuarioRef = db.child("usuarios").child("usuario"+numUsuario[0]);
        novoUsuarioRef.child("email").setValue(emailUsuario);

    }

}
