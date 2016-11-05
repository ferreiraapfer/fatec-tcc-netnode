package com.fatec.fernanda.appredes.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.interfaces.ExplicacaoTesteChildView;
import com.fatec.fernanda.appredes.models.DataWrapper;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Resposta;
import com.fatec.fernanda.appredes.models.Teste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExplicacaoTesteActivity extends AppCompatActivity {

    DatabaseReference questoesRef;
    DatabaseReference respostasRef;
    int idQuestao;
    int numQuestoes;
    int idConteudo;

    double nota;

    ArrayList<Teste> arrayMeuTeste;

    ArrayList<String> arrayPerguntas;

    ArrayList<Questao> questoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacao_teste);

        Intent originIntent = getIntent();
        idQuestao = originIntent.getExtras().getInt("idQuestao");
        idConteudo = originIntent.getExtras().getInt("idConteudo");
        numQuestoes = originIntent.getExtras().getInt("numQuestoes");

        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data");
        arrayMeuTeste = dw.getArrayTeste();

        arrayPerguntas = new ArrayList<>();
        questoes = new ArrayList<>();

        nota = 0;

        questoesRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("conteudo" + idConteudo);

        final LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.linLayoutExplicacao);

        final int[] c = {0};

        //POROCURAR AS QUESTOES
        for (final Teste teste : arrayMeuTeste) {
            DatabaseReference questaoRef = questoesRef.child("questao" + teste.getQuestao().getId());

            questaoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    teste.getQuestao().setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    teste.getQuestao().setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));

                    final Questao questaoCorrente = teste.getQuestao();

                    //PROCURAR RESPOSTAS - CERTA
                    DatabaseReference respostasRef = FirebaseDatabase.getInstance().getReference().child("respostas");
                    respostasRef = respostasRef.child("resposta" + questaoCorrente.getRespostaCorreta().getId());

                    respostasRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            questaoCorrente.getRespostaCorreta().setDescricao(dataSnapshot.child("descricao").getValue(String.class));

                            if (teste.isAcertou()) {
                                nota++;
                            }

                            ExplicacaoTesteChildView child = new ExplicacaoTesteChildView(ExplicacaoTesteActivity.this);

                            if(teste.isAcertou()){
                                child.acertou();
                            }


                            child.setTxtNumPergunta(String.valueOf(c[0] + 1));
                            child.setTxtPergunta(questaoCorrente.getDescricao());
                            child.setTxtResposta("Resposta: " + questaoCorrente.getRespostaCorreta().getDescricao());
                            child.setTxtExplicacao("Explicação: " + teste.getQuestao().getExplicacao());

                            scrollLayout.addView(child);

                            c[0]++;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    //TODO SE ERROU - MOSTRAR TEXTO EM VERMELHO
                    //TODOSE ACERTOU - MOSTRARTEXTO EM VERDE

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        Toast.makeText(this, "Nota: " + nota, Toast.LENGTH_LONG);

    }


    /*
    * TODO Pegar array de perguntas, array de respostas e array de respostas selecionadas
    * TODO Calcular nota do teste e exibir
    * TODO Pegar explicações das questões selecionadas
    * TODO Exibir
    * */
}
