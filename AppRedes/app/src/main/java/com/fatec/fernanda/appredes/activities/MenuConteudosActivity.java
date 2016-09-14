package com.fatec.fernanda.appredes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.dao.FirebaseHelper;
import com.fatec.fernanda.appredes.domain.Conteudo;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MenuConteudosActivity extends AppCompatActivity {

    String firebaseURL = "https://appredes-a8895.firebaseio.com/tb_conteudo";

    ArrayAdapter<String> adapter;
    ListView conteudosList;
    DatabaseReference db;
    FirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conteudos);

        conteudosList = (ListView) findViewById(R.id.menuConteudos);

        //SETUP FIREBASE
        db = FirebaseDatabase.getInstance().getReference("tb_conteudo");
        helper = new FirebaseHelper(db);


        //ADAPTER
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, helper.retrieve());
        conteudosList.setAdapter(adapter);
/*

        db = FirebaseDatabase.getInstance().getReference();

        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                conteudosArrayList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Conteudo c = new Conteudo();
                    c.setTitulo(data.getValue(Conteudo.class).getTitulo());
                    c.setSubtitulo(data.getValue(Conteudo.class).getSubtitulo());

                    conteudosArrayList.add(c);

                }

                if (conteudosArrayList.size() > 0) {
                    adapter = new ArrayAdapter(MenuConteudosActivity.this, android.R.layout.two_line_list_item, conteudosArrayList);
                    conteudosList.setAdapter(adapter);

                } else {
                    Toast.makeText(MenuConteudosActivity.this, "Array menor que zero", Toast.LENGTH_LONG).show();
                }

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
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*


        mFirebaseRef = new Firebase("https://appredes-a8895.firebaseio.com/tb_conteudo");


        mFirebaseRef.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListAdapter = new FirebaseListAdapter<Conteudo>(MenuConteudosActivity.this, Conteudo.class, android.R.layout.two_line_list_item, mFirebaseRef) {
                    @Override
                    protected void populateView(View v, Conteudo model, int position) {
                        ((TextView) v.findViewById(android.R.id.text1)).setText(model.getTitulo());
                        ((TextView) v.findViewById(android.R.id.text2)).setText(model.getSubtitulo());
                    }
                };

                conteudosList.setAdapter(mListAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                String message = "Server error. Refresh page";
                Toast.makeText(MenuConteudosActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });
*/
    }

}
