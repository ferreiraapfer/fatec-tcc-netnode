package com.fatec.fernanda.appredes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import java.util.Iterator;
import java.util.Random;

public class RevisaoActivity extends AppCompatActivity {

    ArrayList<String> idConteudos;

    DatabaseReference questoesRef;
    DatabaseReference respostasQuestaoRef;

    ArrayList<Revisao> arrayMinhaRevisao;
    ArrayList<Resposta> arrayRespostas;

    int numQuestoesConteudos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao);

        Intent originIntent = getIntent();
        idConteudos = originIntent.getExtras().getStringArrayList("idConteudos");
        numQuestoesConteudos = originIntent.getExtras().getInt("numQuestoesConteudos");

        arrayMinhaRevisao = new ArrayList<>();


        respostasQuestaoRef = FirebaseDatabase.getInstance().getReference().child("respostas");
        questoesRef = FirebaseDatabase.getInstance().getReference().child("questoes");

        for (final String idConteudo : idConteudos) {
            questoesRef.child(idConteudo).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    arrayRespostas = new ArrayList<>();

                    Revisao novaRevisao = new Revisao();
                    Questao novaQuestao = new Questao();

                    novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(7)));
                    novaQuestao.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));
                    novaQuestao.setDescricao(dataSnapshot.child("descricao").getValue(String.class));


                    for (final DataSnapshot data : dataSnapshot.child("respostas").getChildren()) {
                        final Resposta novaResposta = new Resposta();

                        novaResposta.setId(Integer.parseInt(data.getKey().substring(8)));

                        if (data.getValue(Boolean.class)) {
                            novaQuestao.setRespostaCorreta(novaResposta);
                        }

                        respostasQuestaoRef.child("questao" + novaResposta.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                novaResposta.setDescricao(data.child("descricao").getValue(String.class));
                                novaResposta.setExplicacao(data.child("explicacao").getValue(String.class));

                                arrayRespostas.add(novaResposta);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    novaQuestao.setRespostas(arrayRespostas);

                    novaRevisao.setQuestao(novaQuestao);
                    novaRevisao.setIdConteudo(idConteudo);

                    arrayMinhaRevisao.add(novaRevisao);

                    if (arrayMinhaRevisao.size() == numQuestoesConteudos) {
                        getDezQuestoes(arrayMinhaRevisao);
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

    private void getDezQuestoes(ArrayList<Revisao> arrayIdQuestoes) {

        ArrayList<Revisao> questoes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            questoes.add((arrayIdQuestoes.get(new Random().nextInt(arrayIdQuestoes.size()))));
        }

        //PEGAR RESPOSTAS
        for (Revisao revisao : questoes) {

            System.out.println("CONTEUDO: " + revisao.getIdConteudo());
            System.out.println("QUESTAO: " + revisao.getQuestao().getId());
            System.out.println("RESPSOTA CERTA" + revisao.getQuestao().getRespostaCorreta().getDescricao());

            //EXIBIR
        }

    }
}
