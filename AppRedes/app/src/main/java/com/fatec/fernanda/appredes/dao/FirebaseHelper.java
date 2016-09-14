package com.fatec.fernanda.appredes.dao;

import com.fatec.fernanda.appredes.domain.Conteudo;
import com.google.android.gms.common.api.BooleanResult;
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

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }


    //READ
    public ArrayList<String> retrieve() {
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
        }
    }


}
