package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.interfaces.ExplicacaoTesteChildView;
import com.fatec.fernanda.appredes.models.DataWrapper;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Teste;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ExplicacaoTesteActivity extends AppCompatActivity {

    DatabaseReference questoesRef;
    int idQuestao;
    int numQuestoes;
    int idConteudo;

    double nota;

    ArrayList<Teste> arrayMeuTeste;

    ArrayList<String> arrayPerguntas;

    ArrayList<Questao> questoes;

    TextView txtNotaFinal;
    Button btnMenuPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacao_teste);

        Intent originIntent = getIntent();
        idQuestao = originIntent.getExtras().getInt("idQuestao");
        idConteudo = originIntent.getExtras().getInt("idConteudo");
        numQuestoes = originIntent.getExtras().getInt("numQuestoes");

        txtNotaFinal = (TextView) findViewById(R.id.txtNota);
        btnMenuPrincipal = (Button) findViewById(R.id.btnMenuPrincipal);

        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data");
        arrayMeuTeste = dw.getArrayTeste();

        arrayPerguntas = new ArrayList<>();
        questoes = new ArrayList<>();

        nota = 0;

        questoesRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("conteudo" + idConteudo);

        final LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.linLayoutExplicacao);

        final int[] c = {0};

        //PROCURAR AS QUESTOES
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

                            ExplicacaoTesteChildView child = new ExplicacaoTesteChildView(ExplicacaoTesteActivity.this);

                            child.setTxtNumPergunta(String.valueOf(c[0] + 1));
                            child.setTxtPergunta(questaoCorrente.getDescricao());
                            child.setImgPergunta(teste.getQuestao().getId(), idConteudo);
                            child.setTxtResposta("Resposta: " + questaoCorrente.getRespostaCorreta().getDescricao());
                            child.setTxtExplicacao("Explicação: " + teste.getQuestao().getExplicacao());


                            if (teste.getIdRespostaDada() == teste.getQuestao().getRespostaCorreta().getId()) {
                                teste.setAcertou(Boolean.TRUE);
                                nota++;
                            } else {
                                child.setTxtColorErrou();
                            }

                            scrollLayout.addView(child);

                            calcNotaFinal(arrayMeuTeste.indexOf(teste));


                            c[0]++;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            System.out.println(arrayMeuTeste.indexOf(teste));
            System.out.println(arrayMeuTeste.size());

            //se for o ultimo
            if (arrayMeuTeste.indexOf(teste) + 1 == arrayMeuTeste.size()) {

            }
        }

        btnMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExplicacaoTesteActivity.this, MenuActivity.class));
            }
        });

    }

    private void calcNotaFinal(int i) {

        if (i + 1 == arrayMeuTeste.size()) {
            nota = (nota * 10) / numQuestoes;
            txtNotaFinal.setText("Nota: " + nota);

            if (nota > 5) {
                concluirConteudo();
                alterarPontuaçao();
            }
        }

    }

    private void alterarPontuaçao() {
        //TODO Alterar pontuacao do usuario

        DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
        usuarioRef = usuarioRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pontuacao");


    }


    private void concluirConteudo() {
        DatabaseReference conteudosConcluidosRef;
        DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
        usuarioRef = usuarioRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        conteudosConcluidosRef = usuarioRef.child("conteudosConcluidos");
        conteudosConcluidosRef.child("conteudo" + idConteudo).setValue(true);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExplicacaoTesteActivity.this, MenuTestesActivity.class));
    }

}
