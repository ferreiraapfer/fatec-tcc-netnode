package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.adapters.ConteudoConcluidoAdapter;
import com.fatec.fernanda.appredes.models.ConteudoConcluido;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PerfilActivity extends AppCompatActivity {

    private TextView txtEmailUsuario;
    private TextView txtNomeUsuario;
    private ProgressBar progresso;
    private TextView conteudosConcluidos;
    private TextView progressoTexto;

    FirebaseAuth firebaseAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef;

    String idUsuario;

    String nomeUsuario;
    String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //TODO arrumar pontuação texto, maximo e barra

        initConteudosConcluidosListView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        txtEmailUsuario = (TextView) findViewById(R.id.email_usuario);
        txtNomeUsuario = (TextView) findViewById(R.id.nome_usuario);
        progresso = (ProgressBar) findViewById(R.id.progresso_barra);
        progressoTexto = (TextView) findViewById(R.id.progresso);
        conteudosConcluidos = (TextView) findViewById(R.id.textConteudosConcluidos);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        idUsuario = user.getUid();

        mUserRef = mRootRef.child("usuarios").child(idUsuario);

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomeUsuario = dataSnapshot.child("nome").getValue(String.class);
                emailUsuario = dataSnapshot.child("email").getValue(String.class);

                txtNomeUsuario.setText(nomeUsuario);
                txtEmailUsuario.setText(emailUsuario);
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


    private ArrayList<ConteudoConcluido> createConteudosConcluidosList() {
        ArrayList<ConteudoConcluido> list = new ArrayList<ConteudoConcluido>();

        for (int i = 1; i <= 5; i++) {
            list.add(ConteudoConcluido.novaInstancia(i));
        }

        return list;
    }

    private void initConteudosConcluidosListView() {
        ListView listView = (ListView) findViewById(R.id.advanced_list_view);

        if (listView == null) {
            return;
        }

        ArrayList<ConteudoConcluido> list = createConteudosConcluidosList();

        ConteudoConcluidoAdapter adapter = new ConteudoConcluidoAdapter(this, R.layout.lista_conteudos_concluidos_item, list);
        listView.setAdapter(adapter);
    }


}
