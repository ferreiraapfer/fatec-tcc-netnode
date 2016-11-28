package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.fragments.RevisaoFragment;
import com.fatec.fernanda.appredes.models.Conteudo;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Resposta;
import com.fatec.fernanda.appredes.models.Revisao;
import com.fatec.fernanda.appredes.models.Teste;
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
    ArrayList<Questao> questoes;
    LinearLayout linLayout;
    Button btnConcluirRevisao;
    Button btnMenuPrincipal;
    ScrollView scrollRevisao;
    ArrayList<RevisaoFragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao);

        linLayout = (LinearLayout) findViewById(R.id.linLayoutRevisao);
        btnConcluirRevisao = (Button) findViewById(R.id.btnConcluirRevisao);
        btnMenuPrincipal = (Button) findViewById(R.id.btnMenuPrincipal);
        scrollRevisao = (ScrollView) findViewById(R.id.scrollRevisao);
        fragments = new ArrayList<>();


        Intent originIntent = getIntent();
        idConteudos = originIntent.getExtras().getStringArrayList("idConteudos");
        numQuestoesConteudos = originIntent.getExtras().getInt("numQuestoesConteudos");
        arrayMinhaRevisao = new ArrayList<>();

        questoes = new ArrayList<>();

        for (final String id : idConteudos) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("questoes").child(id);

            final Conteudo conteudo = new Conteudo();
            conteudo.setId(id);

            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Questao novaQuestao = new Questao();

                    novaQuestao.setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    novaQuestao.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));
                    novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(7)));
                    novaQuestao.setConteudo(conteudo);

                    RevisaoFragment frag = addNewQuestion(novaQuestao);
                    getRespostasFromQuestion(dataSnapshot.child("respostas"), novaQuestao, frag);

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

        btnConcluirRevisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Teste> mRevisao = new ArrayList<Teste>();

                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i).getSelectedRadioButton() == null) {

                        new AlertDialog.Builder(RevisaoActivity.this)
                                .setTitle("Termine a Revisão")
                                .setMessage("Responda todas as questões antes de concluir a revisão")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return;
                    }
                }

                for (RevisaoFragment frag : fragments) {

                    Teste mTeste = new Teste();
                    Questao questaoFragmento = new Questao();

                    for (Questao questao : questoes) {
                        if (questao.getDescricao().equals(frag.getTxtPergunta().getText())) {
                            questaoFragmento = questao;
                            mTeste.setQuestao(questao);
                            break;
                        }
                    }

                    for (Resposta resposta : questaoFragmento.getRespostas()) {
                        if (frag.getSelectedRadioButton().getText() == resposta.getDescricao()) {
                            if (questaoFragmento.getRespostaCorreta() == resposta) {
                                mTeste.setAcertou(Boolean.TRUE);
                            } else {
                                mTeste.setAcertou(Boolean.FALSE);
                            }

                            mRevisao.add(mTeste);
                        }
                    }

                    showExplicacao(mRevisao);
                }
            }
        });

        btnMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuPrincipal = new Intent(RevisaoActivity.this, MenuActivity.class);
                RevisaoActivity.this.startActivity(menuPrincipal);
            }
        });

    }

    private void showExplicacao(ArrayList<Teste> mRevisao) {

        for (RevisaoFragment frag : fragments) {
            frag.disableRadioGroup();

            for (Teste questao : mRevisao) {
                if (frag.getTxtPergunta().getText().equals(questao.getQuestao().getDescricao())) {
                    if (questao.isAcertou() == Boolean.FALSE) {
                        //ERROU
                        frag.setTxtRespostaCerta("ERROU \n Resposta certa: " + questao.getQuestao().getRespostaCorreta().getDescricao());
                        frag.setTxtExplicacao(questao.getQuestao().getExplicacao());
                        frag.setErrou();
                    } else {
                        frag.setTxtRespostaCerta("Acertou");
                        frag.setTxtExplicacao(questao.getQuestao().getExplicacao());
                        frag.setAcertou();
                    }
                }
            }

            btnConcluirRevisao.setVisibility(View.INVISIBLE);
            btnMenuPrincipal.setVisibility(View.VISIBLE);
            scrollRevisao.fullScroll(ScrollView.FOCUS_UP);

        }
    }


    private RevisaoFragment addNewQuestion(final Questao novaQuestao) {

        final RevisaoFragment revisaoFragment = new RevisaoFragment(this);
        revisaoFragment.setTxtPergunta(novaQuestao.getDescricao());

        revisaoFragment.setImgIlustracao(novaQuestao.getId(),
                Integer.parseInt(novaQuestao.getConteudo().getId().substring(8)));

        return revisaoFragment;

    }

    private void getRespostasFromQuestion(DataSnapshot dataSnapshot, final Questao novaQuestao, final RevisaoFragment frag) {
        final ArrayList<Resposta> respostas = new ArrayList<>();

        DatabaseReference respostasRef = FirebaseDatabase.getInstance().getReference().child("respostas");

        final int totalRespostas = (int) dataSnapshot.getChildrenCount();

        final int[] numResposta = {0};

        for (final DataSnapshot data : dataSnapshot.getChildren()) {
            respostasRef.child(data.getKey()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    numResposta[0] = numResposta[0] + 1;

                    Resposta novaResposta = new Resposta();
                    novaResposta.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                    novaResposta.setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    novaResposta.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));

                    if (data.getValue(Boolean.class)) {
                        novaQuestao.setRespostaCorreta(novaResposta);
                    }

                    respostas.add(novaResposta);

                    switch (numResposta[0]) {
                        case 1:
                            frag.setRbtnResposta1(novaResposta.getDescricao());
                            frag.getRbtnResposta1().setId(numResposta[0]);
                            break;
                        case 2:
                            frag.setRbtnResposta2(novaResposta.getDescricao());
                            frag.getRbtnResposta2().setId(numResposta[0]);
                            break;
                        case 3:
                            frag.setRbtnResposta3(novaResposta.getDescricao());
                            frag.getRbtnResposta3().setId(numResposta[0]);
                            break;
                    }

                    if (numResposta[0] == totalRespostas) {
                        addNewView(frag, novaQuestao, respostas);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addNewView(RevisaoFragment frag, Questao questao, ArrayList<Resposta> respostas) {
        questao.setRespostas(respostas);
        questoes.add(questao);

        linLayout.addView(frag);
        fragments.add(frag);

    }


}


