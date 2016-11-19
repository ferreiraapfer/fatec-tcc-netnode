package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fatec.fernanda.appredes.service.FirebaseHelper;
import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.fragments.MenuTopicosFragment;
import com.fatec.fernanda.appredes.models.Topico;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuTopicosActivity extends AppCompatActivity {


    ArrayList<String> arrayStringTopicosConteudo;

    ArrayList<Topico> topicos;
    LinearLayout linearLayout;
    DatabaseReference databaseRef;
    DatabaseReference usuarioRef;
    DatabaseReference conteudoRef;
    int idConteudo;

    ArrayList<MenuTopicosFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_topicos);

        linearLayout = (LinearLayout) findViewById(R.id.linLayoutMenuTopicos);
        topicos = new ArrayList<>();
        arrayStringTopicosConteudo = new ArrayList<>();
        fragments = new ArrayList<>();

        //RECEBENDO O ID DO CONTEUDO
        Intent originIntent = getIntent();
        idConteudo = originIntent.getExtras().getInt("idConteudo");

        //SETUP FIREBASE
        conteudoRef = FirebaseDatabase.getInstance().getReference().child("conteudos").child("conteudo" + idConteudo).child("topicos");

        //pegando os topicos do conteudo
        conteudoRef.addChildEventListener(new ChildEventListener() {
                                              @Override
                                              public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                  databaseRef = FirebaseDatabase.getInstance().getReference().child("topicos").child(dataSnapshot.getKey());
                                                  usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios")
                                                          .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("topicosConcluidos").child("conteudo" + idConteudo);

                                                  //pegando os atributos do topico
                                                  databaseRef.addValueEventListener(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                            final Topico novoTopico = new Topico();

                                                                                            novoTopico.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
                                                                                            novoTopico.setId(Integer.parseInt(dataSnapshot.getKey().substring(6)));

                                                                                            final MenuTopicosFragment child = new MenuTopicosFragment(MenuTopicosActivity.this);
                                                                                            child.setCheckedTextView(novoTopico.getTitulo());
                                                                                            child.setId(novoTopico.getId());

                                                                                            topicos.add(novoTopico);

                                                                                            child.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View view) {
                                                                                                    for (Topico t : topicos) {
                                                                                                        if (t.getTitulo().equals(child.getCheckedTextView())) {
                                                                                                            Intent topicoIntent = new Intent(MenuTopicosActivity.this, TopicoActivity.class);

                                                                                                            topicoIntent.putExtra("idTopico", t.getId());
                                                                                                            topicoIntent.putExtra("tituloTopico", t.getTitulo());
                                                                                                            topicoIntent.putExtra("idConteudo", idConteudo);

                                                                                                            MenuTopicosActivity.this.startActivity(topicoIntent);
                                                                                                        }
                                                                                                    }

                                                                                                }
                                                                                            });

                                                                                            fragments.add(child);
                                                                                            getListFinish(child, novoTopico);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                                        }
                                                                                    }

                                                  );

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
                                          }

        );
    }

    private void getListFinish(final MenuTopicosFragment child, final Topico topico) {
        System.out.println("getListFinish");

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    linearLayout.addView(child);
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        usuarioRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("if " + topico);
                if (("topico" + topico.getId()).equals(dataSnapshot.getKey())) {
                    if (dataSnapshot.getValue(Boolean.class)) {
                        child.setChecked();
                    }
                    linearLayout.addView(child);
                    return;
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

}
