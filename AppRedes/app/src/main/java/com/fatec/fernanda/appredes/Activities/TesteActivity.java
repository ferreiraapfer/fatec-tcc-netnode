package com.fatec.fernanda.appredes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.DataWrapper;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Resposta;
import com.fatec.fernanda.appredes.models.Teste;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TesteActivity extends AppCompatActivity {

    ProgressBar progBarTotalQuestoes;

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

    ArrayList<String> idQuestoes;
    ArrayList<String> arrayStringRespostas;

    int idConteudo;
    int numQuestoes;
    int idProxQuestao;
    int qtdRespostas;
    int contQuestao;

    ArrayList<Teste> arrayMeuTeste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);


        //INICIALIZANDO VALORES
        progBarTotalQuestoes = (ProgressBar) findViewById(R.id.progBarTotalQuestoes);

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

        contQuestao = 0;

        final Intent proxQuestaoIntent = new Intent(TesteActivity.this, TesteActivity.class);


        //RECEBENDO O ID DA QUESTAO
        Intent originIntent = getIntent();
        novaQuestao.setId(originIntent.getExtras().getInt("idQuestao"));
        idConteudo = originIntent.getExtras().getInt("idConteudo");
        numQuestoes = originIntent.getExtras().getInt("numQuestoes");
        contQuestao = originIntent.getExtras().getInt("contQuestao");
        idQuestoes = originIntent.getExtras().getStringArrayList("idQuestoes");

        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data");
        arrayMeuTeste = dw.getArrayTeste();
        dataWrapper = new DataWrapper();

        questoesConteudoRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("conteudo" + idConteudo);
        respostasRef = FirebaseDatabase.getInstance().getReference().child("respostas");


        //PEGA PRIMEIRA QUESTAO DO CONTEUDO, ID DE TODAS AS QUESTOES, NUM TOTAL DE QUESTOES, NUMQUESTAO ATUAL
        //Se for a primeira questão do teste
        if (novaQuestao.getId() == 0) {
            idQuestoes = new ArrayList<>();

            questoesConteudoRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    idQuestoes.add(dataSnapshot.getKey().substring(7));

                    numQuestoes = numQuestoes + 1;

                    if (novaQuestao.getId() == 0) {
                        novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(7)));
                        questaoRef = questoesConteudoRef.child("questao" + novaQuestao.getId());

                        getQuestao();
                    }

                    contQuestao = 1;

                    //Se não for a última questão, novo Intent
                    if (numQuestoes > 1) {
                        idProxQuestao = Integer.parseInt(idQuestoes.get(contQuestao));

                        btnProxQuestao.setVisibility(View.VISIBLE);
                        btnConcluirTeste.setVisibility(View.INVISIBLE);
                    } else {
                        btnProxQuestao.setVisibility(View.INVISIBLE);
                        btnConcluirTeste.setVisibility(View.VISIBLE);
                    }

                    //CRIANDO INTENT
                    proxQuestaoIntent.putExtra("idQuestao", idProxQuestao);
                    proxQuestaoIntent.putExtra("numQuestoes", numQuestoes);
                    proxQuestaoIntent.putExtra("idConteudo", idConteudo);
                    proxQuestaoIntent.putExtra("idQuestoes", idQuestoes);
                    proxQuestaoIntent.putExtra("contQuestao", contQuestao + 1);
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
        } else {

            //Se não for a ultima questão, novoIntent
            if (contQuestao < numQuestoes) {
                idProxQuestao = Integer.parseInt(idQuestoes.get(contQuestao));
            } else {
                btnProxQuestao.setVisibility(View.INVISIBLE);
                btnConcluirTeste.setVisibility(View.VISIBLE);
            }

            questaoRef = questoesConteudoRef.child("questao" + novaQuestao.getId());
            getQuestao();

            //CRIANDO INTENT
            proxQuestaoIntent.putExtra("idQuestao", idProxQuestao);
            proxQuestaoIntent.putExtra("numQuestoes", numQuestoes);
            proxQuestaoIntent.putExtra("idConteudo", idConteudo);
            proxQuestaoIntent.putExtra("idQuestoes", idQuestoes);
            proxQuestaoIntent.putExtra("contQuestao", contQuestao + 1);
        }

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

    public void getQuestao() {

        questaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // exibir questao
                txtPergunta.setText(dataSnapshot.child("descricao").getValue(String.class));

                progBarTotalQuestoes.setProgress(contQuestao);
                progBarTotalQuestoes.setMax(numQuestoes);

                //Quantidade de repostas
                qtdRespostas = (int) (dataSnapshot.child("respostas").getChildrenCount());

                DatabaseReference respostasRef = questaoRef.child("respostas");

                final int[] count = {0};

                respostasRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //se for a resposta certa
                        if (dataSnapshot.getValue(Boolean.class)) {
                            respostaCerta.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                            novaQuestao.setRespostaCorreta(respostaCerta);

                            getResposta(novaQuestao.getRespostaCorreta().getId(), count[0]);
                        } else {
                            getResposta(Integer.parseInt(dataSnapshot.getKey().substring(8)), count[0]);
                        }

                        getImagem(Integer.parseInt(dataSnapshot.getKey().substring(8)));

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
    }

    private void getImagem(int idQuestao) {
        StorageReference questaoRef = FirebaseStorage.getInstance().getReference().child("questoes")
                .child("conteudo" + idConteudo).child("questao" + idQuestao + ".png");

        questaoRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                imgIlustracao.setImageBitmap(bmp);
                imgIlustracao.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle any erros

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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Voltar ao menu")
                .setMessage("Deseja voltar ao menu principal?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(TesteActivity.this, MenuActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}
