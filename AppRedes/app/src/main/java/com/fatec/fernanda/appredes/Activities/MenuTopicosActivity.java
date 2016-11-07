package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fatec.fernanda.appredes.dao.FirebaseHelper;
import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.interfaces.MenuTopicosChildView;
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

    ArrayAdapter adapter;

    ListView topicosList;


    DatabaseReference topicosConteudoRef;
    DatabaseReference topicosRef;
    DatabaseReference umTituloRef;

    FirebaseHelper helper;


    ArrayList<String> arrayStringTopicos;
    ArrayList<String> titulosTopicos;

    Topico topico;


    ArrayList<Topico> topicos;
    LinearLayout linearLayout;
    DatabaseReference databaseRef;
    DatabaseReference usuarioRef;
    int idConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_topicos);

        linearLayout = (LinearLayout) findViewById(R.id.linLayoutMenuTopicos);
        topicos = new ArrayList<>();

        //RECEBENDO O ID DO CONTEUDO
        Intent originIntent = getIntent();
        idConteudo = originIntent.getExtras().getInt("idConteudo");

        //SETUP FIREBASE
        databaseRef = FirebaseDatabase.getInstance().getReference().child("topicos");
        usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("topicosConcluidos").child("conteudo" + idConteudo);

        databaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Topico novoTopico = new Topico();

                novoTopico.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
                novoTopico.setId(Integer.parseInt(dataSnapshot.getKey().substring(6)));

                final MenuTopicosChildView child = new MenuTopicosChildView(MenuTopicosActivity.this);
                child.setCheckedTextView(novoTopico.getTitulo());

                //VERIFICA SE JÁ CONCLUIU
                usuarioRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String idTopico = "topico" + novoTopico.getId();
                        if (idTopico.equals(dataSnapshot.getKey())) {
                            child.setChecked();
                            child.setClickable(Boolean.FALSE);
                        }

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
                        linearLayout.addView(child);


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




        /*

        topicosList = (ListView) findViewById(R.id.lstMenuTopicos);

        //RECEBENDO O ID DO CONTEUDO
        Intent originIntent = getIntent();
        idConteudo = originIntent.getExtras().getInt("idConteudo");

        //SETUP FIREBASE
        databaseRef = FirebaseDatabase.getInstance().getReference();

        topicosConteudoRef = databaseRef.child("conteudos").child("conteudo"+idConteudo).child("topicos");
        System.out.println(topicosConteudoRef.toString());

        helper = new FirebaseHelper(topicosConteudoRef);

        topicosRef = databaseRef.child("topicos");


        ////TESTANDO MENU
        arrayTopicos = new ArrayList<>();
        arrayStringTopicos = new ArrayList<>(); // topicos que eu tenho no conteudo

        topicosConteudoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    arrayStringTopicos.add(ds.getKey().toString());
                }

                //PEGANDO TITULO
                titulosTopicos = new ArrayList<>();
                arrayTopicos = new ArrayList<>();

                for (final String topicoCorrente : arrayStringTopicos) {
                    umTituloRef = topicosRef.child(topicoCorrente).child("titulo");

                    umTituloRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            titulosTopicos.add(dataSnapshot.getValue(String.class));

                            topico = new Topico();
                            topico.setId(Integer.parseInt(topicoCorrente.substring(6))); //TODO Deixar id como string
                            topico.setTitulo(dataSnapshot.getValue(String.class));
                            arrayTopicos.add(topico);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } //

                adapter = new ArrayAdapter<>(MenuTopicosActivity.this, android.R.layout.simple_list_item_1, titulosTopicos);
                topicosList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        topicosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent topicoIntent = new Intent(MenuTopicosActivity.this, TopicoActivity.class);

                for (Topico t : arrayTopicos) {
                    if (t.getTitulo() == adapterView.getAdapter().getItem(i).toString()) {
                        topicoIntent.putExtra("idTopico", t.getId());
                        topicoIntent.putExtra("tituloTopico", t.getTitulo());
                        topicoIntent.putExtra("idConteudo", idConteudo);
                    }
                }

                MenuTopicosActivity.this.startActivity(topicoIntent);


            }
        });


        */
    }
}
