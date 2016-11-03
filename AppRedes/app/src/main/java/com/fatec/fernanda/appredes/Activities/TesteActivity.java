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

    ArrayList<String> arrayStringRespostas;

    int idConteudo;
    int idQuestao;
    int numQuestoes;
    int idProxQuestao;

    int qtdRespostas;

    String respostaCerta;

    ArrayList<Resposta> arrayRespostas;
    ArrayList<Teste> arrayMeuTeste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        imgIlustracao = (ImageView) findViewById(R.id.imgIlustracao);

        radioGroup = (RadioGroup) findViewById(R.id.rgrpRespostas);
        rbResposta1 = (RadioButton) findViewById(R.id.rbtnResposta1);
        rbResposta2 = (RadioButton) findViewById(R.id.rbtnResposta2);
        rbResposta3 = (RadioButton) findViewById(R.id.rbtnResposta3);

        btnProxQuestao = (Button) findViewById(R.id.btnProxima);
        btnConcluirTeste = (Button) findViewById(R.id.btnConcluirTeste);


        arrayStringRespostas = new ArrayList<>();
        arrayRespostas = new ArrayList<>();

        /*
        done Receber index da questao pelo intent (Colocar intent a partir da seleção no menu).
        done Exibir perguntas
        done Exibir respostas
        done Contar quantas questoes tem. Se essa não for aúltima, colocar o index no intent seguinte
        TODO Botão de concluir teste
        TODO Calcular respostas certas e nota
        TODO Ao final, exibir botão de voltar ao menu principal
        TODO Ao final, exibir teste com resposta certa e explicações
         */


        final Intent proxQuestaoIntent = new Intent(TesteActivity.this, TesteActivity.class);


        //RECEBENDO O ID DA QUESTAO
        Intent originIntent = getIntent();
        idQuestao = originIntent.getExtras().getInt("idQuestao");
        idConteudo = originIntent.getExtras().getInt("idConteudo");
        numQuestoes = originIntent.getExtras().getInt("numQuestoes");
        arrayMeuTeste = (ArrayList<Teste>) originIntent.getExtras().getSerializable("arrayMeuTeste");

        questoesConteudoRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("conteudo" + idConteudo);
        questaoRef = questoesConteudoRef.child("questao" + idQuestao);
        respostasRef = FirebaseDatabase.getInstance().getReference().child("respostas");

        //CONTANDO QUANTAS QUESTOES TEM
        if (idQuestao == 1) {
            questoesConteudoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    numQuestoes = (int) dataSnapshot.getChildrenCount();


                    //Se não for a ultima questão, novoIntent
                    if (idQuestao < numQuestoes) {
                        idProxQuestao = idQuestao + 1;
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
            if (idQuestao < numQuestoes) {
                idProxQuestao = idQuestao + 1;
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
                            respostaCerta = dataSnapshot.getKey();
                        }

                        count[0] = count[0] + 1;

                        getResposta(dataSnapshot.getKey(), count[0]);
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
                    String myResposta = "resposta" + radioGroup.getCheckedRadioButtonId();

                    Teste meuTeste = new Teste();
                    meuTeste.setIdQuestao(idQuestao);
                    meuTeste.setIdRespostaDada(radioGroup.getCheckedRadioButtonId());

                    if (myResposta.equals(respostaCerta)) {
                        meuTeste.setAcertou(TRUE);
                    }

                    arrayMeuTeste.add(meuTeste);

                    proxQuestaoIntent.putExtra("arrayMeuTeste",arrayMeuTeste);

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
                Intent explicacaoTeste = new Intent(TesteActivity.this, ExplicacaoTesteActivity.class);

                explicacaoTeste.putExtra("idConteudo",idConteudo);
                explicacaoTeste.putExtra("arrayMeuTeste", arrayMeuTeste);
                explicacaoTeste.putExtra("numQuestoes", numQuestoes);

                TesteActivity.this.startActivity(explicacaoTeste);
            }
        });

    }

    public void getResposta(final String idResposta, final int num) {
        respostasRef.child(idResposta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Resposta novaResposta = new Resposta();
                novaResposta.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                novaResposta.setDescricao(dataSnapshot.child("descricao").getValue().toString());
                novaResposta.setExplicacao(dataSnapshot.child("explicacao").getValue().toString());

                arrayRespostas.add(novaResposta);

                switch (num) {
                    case 1:
                        rbResposta1.setText(arrayRespostas.get(0).getDescricao());
                        rbResposta1.setId(arrayRespostas.get(0).getId());
                        break;
                    case 2:
                        rbResposta2.setText(arrayRespostas.get(1).getDescricao());
                        rbResposta2.setId(arrayRespostas.get(1).getId());
                        break;
                    case 3:
                        rbResposta3.setText(arrayRespostas.get(2).getDescricao());
                        rbResposta3.setId(arrayRespostas.get(2).getId());
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
