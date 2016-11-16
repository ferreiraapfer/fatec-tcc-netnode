package com.fatec.fernanda.appredes.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.fragments.OnGetDataListener;
import com.fatec.fernanda.appredes.fragments.RevisaoFragment;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Resposta;
import com.fatec.fernanda.appredes.models.Revisao;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RevisaoActivity extends AppCompatActivity {

    ArrayList<String> idConteudos;
    ArrayList<Revisao> arrayMinhaRevisao;
    int numQuestoesConteudos;

    private ProgressDialog dialog;
    Revisao novaRevisao;
    Questao novaQuestao;

    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao);

        linLayout = (LinearLayout) findViewById(R.id.linLayoutRevisao);

        Intent originIntent = getIntent();
        idConteudos = originIntent.getExtras().getStringArrayList("idConteudos");
        numQuestoesConteudos = originIntent.getExtras().getInt("numQuestoesConteudos");
        arrayMinhaRevisao = new ArrayList<>();

        dialog = ProgressDialog.show(RevisaoActivity.this, "Download", "downloading");

        /*
        TODO pegar info da questão em uma task
        TODO exibir info da questao
        TODO pegar info das respostas em uma task
        TODO exibir info das respostas
        TODO adicionar childView
         */

        for (String idConteudo : idConteudos) {
            DatabaseReference questaoRef = FirebaseDatabase.getInstance().getReference()
                    .child("questoes").child(idConteudo);

            questaoRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    novaRevisao = new Revisao();
                    novaQuestao = new Questao();

                    novaQuestao.setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    novaQuestao.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));
                    novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(7)));

                    ArrayList<Resposta> respostasQuestao = new ArrayList<Resposta>();

                    for (DataSnapshot idResposta : dataSnapshot.child("respostas").getChildren()) {
                        respostasQuestao.add(mCheckInforInServer(idResposta.getKey()));

                        if (idResposta.getValue(Boolean.class)) {
                            novaQuestao.setRespostaCorreta(respostasQuestao.get(respostasQuestao.size() - 1));
                        }
                    }

                    RevisaoFragment child = new RevisaoFragment(RevisaoActivity.this);
                    child.setTxtPergunta(novaQuestao.getDescricao());


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

    public void mReadDataOnce(String child, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseDatabase.getInstance().getReference().child("respostas")
                .child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    private Resposta mCheckInforInServer(String child) {
        final Resposta[] novaResposta = new Resposta[1];

        new RevisaoActivity().mReadDataOnce(child, new OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                novaResposta[0] = new Resposta();

                novaResposta[0].setDescricao(data.child("descricao").getValue(String.class));
                novaResposta[0].setExplicacao(data.child("explicacao").getValue(String.class));
                novaResposta[0].setId(Integer.parseInt(data.getKey().substring(8)));

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        });

        return novaResposta[0];
    }

}


