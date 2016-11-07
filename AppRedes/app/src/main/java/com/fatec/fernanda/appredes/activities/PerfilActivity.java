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

        //initTesteRealizadoListView(mUserRef);

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

                getConteudosConcluidos(mUserRef);

                mUserRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getConteudosConcluidos(DatabaseReference mUserRef) {
        /*
            TODO
            -Pegar quais conteudos concluiu e as notas
            -Pegar os nomes dos conteudos que concluiu
            -Exibir Nome do conteudo, e a nota
        */

        //idConteudo, nomeConteudo, nota
        final ArrayList<TesteRealizado> testesRealizados = new ArrayList<>();

        mUserRef = mUserRef.child("conteudosConcluidos");

        final DatabaseReference conteudosRef = FirebaseDatabase.getInstance().getReference().child("conteudos");

        mUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final TesteRealizado novoTeste = new TesteRealizado();
                novoTeste.setIdConteudo(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                novoTeste.setNotaTeste(dataSnapshot.getValue(double.class));

                //Pegar nome do conteudo
                conteudosRef.child("conteudo" + novoTeste.getIdConteudo()).child("titulo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        novoTeste.setNomeConteudo(dataSnapshot.getValue(String.class));
                        initTesteRealizadoListView(newTeste(novoTeste, testesRealizados));
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

    private ArrayList<TesteRealizado> newTeste(TesteRealizado novoTeste, ArrayList<TesteRealizado> testesRealizados) {
        testesRealizados.add(novoTeste);
        return testesRealizados;
    }

    private void initTesteRealizadoListView(ArrayList<TesteRealizado> list) {
        ListView listView = (ListView) findViewById(R.id.advanced_list_view);

        if (listView == null) {
            return;
        }

        TesteRealizadoAdapter adapter = new TesteRealizadoAdapter(this, R.layout.lista_testes_realizados_item, list);
        listView.setAdapter(adapter);

    }
}
