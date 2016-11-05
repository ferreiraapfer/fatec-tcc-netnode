package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.DataWrapper;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Resposta;
import com.fatec.fernanda.appredes.models.Teste;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class TesteActivity extends AppCompatActivity {

    TextView txtPergunta;
    ImageView imgIlustracao;

    RadioGroup radioGroup;

    RadioButton rbResposta1;
    RadioButton rbResposta2;
    RadioButton rbResposta3;

    Button btnProxQuestao;
    Button btnConcluirTeste;

    DatabaseReference questaoRef;
    DatabaseReference questoesConteudoRef;
    DatabaseReference respostasRef;

    Questao novaQuestao;
    ArrayList<Resposta> respostas;
    Resposta respostaCerta;

    DataWrapper dataWrapper;

    ArrayList<String> arrayStringRespostas;

    int idConteudo;
    int numQuestoes;
    int idProxQuestao;

    int qtdRespostas;

    ArrayList<Teste> arrayMeuTeste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);


        //INICIALIZANDO VALORES
        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        imgIlustracao = (ImageView) findViewById(R.id.imgIlustracao);

        radioGroup = (RadioGroup) findViewById(R.id.rgrpRespostas);
        rbResposta1 = (RadioButton) findViewById(R.id.rbtnResposta1);
        rbResposta2 = (RadioButton) findViewById(R.id.rbtnResposta2);
        rbResposta3 = (RadioButton) findViewById(R.id.rbtnResposta3);

        btnProxQuestao = (Button) findViewById(R.id.btnProxima);
        btnConcluirTeste = (Button) findViewById(R.id.btnConcluirTeste);

        arrayStringRespostas = new ArrayList<>();
        respostas = new ArrayList<>();


        novaQuestao = new Questao();
        respostas = new ArrayList<>();
        respostaCerta = new Resposta();

        /*
        done Receber index da questao pelo intent (Colocar intent a partir da seleção no menu).
        done Exibir perguntas
        done Exibir respostas
        done Contar quantas questoes tem. Se essa não for aúltima, colocar o index no intent seguinte
        done Botão de concluir teste
        TODO Calcular respostas certas e nota
        TODO Ao final, exibir botão de voltar ao menu principal
        TODO Ao final, exibir teste com resposta certa e explicações
         */


        final Intent proxQuestaoIntent = new Intent(TesteActivity.this, TesteActivity.class);


        //RECEBENDO O ID DA QUESTAO
        Intent originIntent = getIntent();
        novaQuestao.setId(originIntent.getExtras().getInt("idQuestao"));
        idConteudo = originIntent.getExtras().getInt("idConteudo");
        numQuestoes = originIntent.getExtras().getInt("numQuestoes");

        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data");
        arrayMeuTeste = dw.getArrayTeste();

        dataWrapper = new DataWrapper();

        questoesConteudoRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("conteudo" + idConteudo);
        questaoRef = questoesConteudoRef.child("questao" + novaQuestao.getId());
        respostasRef = FirebaseDatabase.getInstance().getReference().child("respostas");


        //CONTANDO QUANTAS QUESTOES TEM
        if (novaQuestao.getId() == 1) {
            questoesConteudoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    numQuestoes = (int) dataSnapshot.getChildrenCount();


                    //Se não for a ultima questão, novoIntent
                    if (novaQuestao.getId() < numQuestoes) {
                        idProxQuestao = novaQuestao.getId() + 1;
                    } else {
                        btnProxQuestao.setVisibility(View.INVISIBLE);
                        btnConcluirTeste.setVisibility(View.VISIBLE);
                    }


                    //CRIANDO INTENT
                    proxQuestaoIntent.putExtra("idQuestao", idProxQuestao);
                    proxQuestaoIntent.putExtra("numQuestoes", numQuestoes);
                    proxQuestaoIntent.putExtra("idConteudo", idConteudo);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            //Se não for a ultima questão, novoIntent
            if (novaQuestao.getId() < numQuestoes) {
                idProxQuestao = novaQuestao.getId() + 1;
            } else {
                btnProxQuestao.setVisibility(View.INVISIBLE);
                btnConcluirTeste.setVisibility(View.VISIBLE);
            }
            //CRIANDO INTENT
            proxQuestaoIntent.putExtra("idQuestao", idProxQuestao);
            proxQuestaoIntent.putExtra("numQuestoes", numQuestoes);
            proxQuestaoIntent.putExtra("idConteudo", idConteudo);

        }

        //TODO adicionar as respostas certas/erradas do usuario
        questaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // exibir questao
                txtPergunta.setText(dataSnapshot.child("descricao").getValue(String.class));

                //Quantidade de repostas
                qtdRespostas = (int) (dataSnapshot.child("respostas").getChildrenCount());

                DatabaseReference respostasRef = questaoRef.child("respostas");

                final int[] count = {0};

                respostasRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue(Boolean.class)) {
                            respostaCerta.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                            novaQuestao.setRespostaCorreta(respostaCerta);

                            getResposta(novaQuestao.getRespostaCorreta().getId(), count[0]);
                        } else {
                            getResposta(Integer.parseInt(dataSnapshot.getKey().substring(8)), count[0]);
                        }

                        count[0] = count[0] + 1;

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
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnProxQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(TesteActivity.this, "Selecione uma resposta para continuar", Toast.LENGTH_SHORT);
                } else {
                    novaQuestao.setRespostas(respostas);

                    Teste meuTeste = new Teste();
                    meuTeste.setQuestao(novaQuestao);
                    meuTeste.setIdRespostaDada(radioGroup.getCheckedRadioButtonId());

                    String myResposta = "resposta" + radioGroup.getCheckedRadioButtonId();
                    if (myResposta.equals(respostaCerta)) {
                        meuTeste.setAcertou(Boolean.TRUE);
                    }

                    arrayMeuTeste.add(meuTeste);

                    dataWrapper.setArrayTeste(arrayMeuTeste);

                    proxQuestaoIntent.putExtra("data", dataWrapper);

                    TesteActivity.this.startActivity(proxQuestaoIntent);
                }


                //pegar a resposta dada, colocar em um array? e colocar no intent
                //validar se clicou em uma questao


                //


            }
        });

        btnConcluirTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(TesteActivity.this, "Selecione uma resposta para continuar", Toast.LENGTH_SHORT);
                } else {

                    novaQuestao.setRespostas(respostas);

                    Teste meuTeste = new Teste();
                    meuTeste.setQuestao(novaQuestao);
                    meuTeste.setIdRespostaDada(radioGroup.getCheckedRadioButtonId());

                    String myResposta = "resposta" + radioGroup.getCheckedRadioButtonId();
                    if (myResposta.equals(respostaCerta)) {
                        meuTeste.setAcertou(Boolean.TRUE);
                    }

                    arrayMeuTeste.add(meuTeste);

                    Intent explicacaoTeste = new Intent(TesteActivity.this, ExplicacaoTesteActivity.class);

                    explicacaoTeste.putExtra("idConteudo", idConteudo);
                    explicacaoTeste.putExtra("numQuestoes", numQuestoes);

                    dataWrapper.setArrayTeste(arrayMeuTeste);

                    explicacaoTeste.putExtra("data", dataWrapper);

                    TesteActivity.this.startActivity(explicacaoTeste);
                }
            }
        });

    }

    public void getResposta(final int idResposta, final int num) {

        respostasRef.child("resposta" + idResposta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Resposta novaResposta = new Resposta();
                novaResposta.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                novaResposta.setDescricao(dataSnapshot.child("descricao").getValue().toString());

                respostas.add(novaResposta);

                switch (num) {
                    case 0:
                        rbResposta1.setText(respostas.get(0).getDescricao());
                        rbResposta1.setId(respostas.get(0).getId());
                        break;
                    case 1:
                        rbResposta2.setText(respostas.get(1).getDescricao());
                        rbResposta2.setId(respostas.get(1).getId());
                        break;
                    case 2:
                        rbResposta3.setText(respostas.get(2).getDescricao());
                        rbResposta3.setId(respostas.get(2).getId());
                        rbResposta3.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
