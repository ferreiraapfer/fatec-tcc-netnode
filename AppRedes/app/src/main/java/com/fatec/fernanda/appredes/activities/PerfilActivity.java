package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.adapters.TesteRealizadoAdapter;
import com.fatec.fernanda.appredes.models.TesteRealizado;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PerfilActivity extends AppCompatActivity {

    private TextView txtEmailUsuario;
    private TextView txtNomeUsuario;
    private ProgressBar pbarProgresso;
    private TextView conteudosConcluidos;
    private TextView txtProgressoUsuario;

    FirebaseAuth firebaseAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef;

    String idUsuario;

    ArrayList<TesteRealizado> testesRealizados;

    String nomeUsuario;
    String emailUsuario;
    int progressoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //TODO arrumar pontuação texto, maximo e barra

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        txtEmailUsuario = (TextView) findViewById(R.id.email_usuario);
        txtNomeUsuario = (TextView) findViewById(R.id.nome_usuario);
        pbarProgresso = (ProgressBar) findViewById(R.id.progressoBarra);
        txtProgressoUsuario = (TextView) findViewById(R.id.progresso);
        conteudosConcluidos = (TextView) findViewById(R.id.textConteudosConcluidos);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        idUsuario = user.getUid();

        mUserRef = mRootRef.child("usuarios").child(idUsuario);

        initTesteRealizadoListView(mUserRef);


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                nomeUsuario = dataSnapshot.child("nome").getValue(String.class);
                emailUsuario = dataSnapshot.child("email").getValue(String.class);
                progressoUsuario = dataSnapshot.child("progresso").getValue(Integer.class);

                txtNomeUsuario.setText(nomeUsuario);
                txtEmailUsuario.setText(emailUsuario);

                pbarProgresso.setProgress(progressoUsuario);
                txtProgressoUsuario.setText(String.valueOf(progressoUsuario)); //TODO Porcentagem do total de conteudos

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                conteudosConcluidos.setText(text);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        */


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private ArrayList<TesteRealizado> createTesteRealizadoList() {
        ArrayList<TesteRealizado> list = new ArrayList<TesteRealizado>();

        for (int i = 1; i <= 5; i++) {
            list.add(TesteRealizado.novaInstancia(i));
        }

        return list;
    }

    private void initTesteRealizadoListView(DatabaseReference usuarioRef) {
        ListView listView = (ListView) findViewById(R.id.advanced_list_view);

        if (listView == null) {
            return;
        }

        /*TODO Pegar a lista de conteudos. Comparar com os testes realizados, pegar no array o nome, id do conteudo e a nota no teste

        usuarioRef.child("testesRealizados").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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

        */


        ArrayList<TesteRealizado> list = createTesteRealizadoList();

        TesteRealizadoAdapter adapter = new TesteRealizadoAdapter(this, R.layout.lista_testes_realizados_item, list);
        listView.setAdapter(adapter);

    }


}
