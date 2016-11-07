package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.dao.FirebaseHelper;
import com.fatec.fernanda.appredes.interfaces.MenuConteudosChildView;
import com.fatec.fernanda.appredes.models.Conteudo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuConteudosActivity extends AppCompatActivity {

    DatabaseReference db;
    DatabaseReference usuarioRef;

    ArrayList<Conteudo> conteudos;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conteudos);

        linearLayout = (LinearLayout) findViewById(R.id.linLayoutMenuConteudos);

        conteudos = new ArrayList<>();

        //SETUP FIREBASE
        db = FirebaseDatabase.getInstance().getReference("conteudos");
        usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("conteudosConcluidos");

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Conteudo novoConteudo = new Conteudo();

                novoConteudo.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
                novoConteudo.setId(dataSnapshot.getKey());

                final MenuConteudosChildView child = new MenuConteudosChildView(MenuConteudosActivity.this);
                child.setCheckedTextView(novoConteudo.getTitulo());

                //VERIFICAR SE JÁ TERMINOU
                usuarioRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (novoConteudo.getId().equals(dataSnapshot.getKey())) {
                            child.setChecked();
                            child.setClickable(Boolean.FALSE);

                            conteudos.add(novoConteudo);

                            child.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    for (Conteudo c : conteudos) {
                                        if (c.getTitulo().equals(child.getCheckedTextView())) {
                                            Intent menuTopicosIntent = new Intent(MenuConteudosActivity.this,
                                                    MenuTopicosActivity.class);
                                            menuTopicosIntent.putExtra("idConteudo",
                                                    Integer.parseInt(c.getId().substring(8)));

                                            MenuConteudosActivity.this.startActivity(menuTopicosIntent);
                                        }
                                    }
                                }
                            });

                            linearLayout.addView(child);
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
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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


    }


}
