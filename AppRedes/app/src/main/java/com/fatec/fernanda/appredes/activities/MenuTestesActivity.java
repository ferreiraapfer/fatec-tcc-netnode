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

import com.fatec.fernanda.appredes.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuTestesActivity extends AppCompatActivity {

    TextView txtPergunta;
    ImageView imgIlustracao;

    RadioGroup radioGroup;
    RadioButton rbResposta1;
    RadioButton rbResposta2;
    RadioButton rbResposta3;

    Button btnProxQuestao;
    Button btnConcluirTeste;

    DatabaseReference testeRef;
    DatabaseReference todasQuestoesRef;
    DatabaseReference respostasRef;

    ArrayList<String> arrayRespostas;

    int idQuestao;
    int numQuestoes;
    int idProxQuestao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_testes);

        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        imgIlustracao = (ImageView) findViewById(R.id.imgIlustracao);

        radioGroup = (RadioGroup) findViewById(R.id.rgrpRespostas);
        rbResposta1 = (RadioButton) findViewById(R.id.rbtnResposta1);
        rbResposta2 = (RadioButton) findViewById(R.id.rbtnResposta2);
        rbResposta3 = (RadioButton) findViewById(R.id.rbtnResposta3);

        btnProxQuestao = (Button) findViewById(R.id.btnProxima);
        btnConcluirTeste = (Button) findViewById(R.id.btnConcluirTeste);

        todasQuestoesRef = FirebaseDatabase.getInstance().getReference().child("questoes");

        arrayRespostas = new ArrayList<>();

        /*
        Receber index da questao pelo intent. TODO Colocar intent a partir da seleção no menu
        Contar quantas questoes tem. Se essa não for aúltima, colocar o index no intent seguinte
         */

        //RECEBENDO O ID DA QUESTAO
        Intent originIntent = getIntent();
        idQuestao = originIntent.getExtras().getInt("idQuestao");
        System.out.println("ID QUESTAO: " + idQuestao);

        testeRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("questao"+idQuestao);
        respostasRef = testeRef.child("respostas");

        //CONTANDO QUANTAS QUESTOES TEM
        todasQuestoesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                numQuestoes = (int) dataSnapshot.getChildrenCount();
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

        //Se não for a ultima questão, novoIntent
        if (idQuestao != numQuestoes) {
            idProxQuestao = idQuestao+1;
        } else {
            btnProxQuestao.setVisibility(View.INVISIBLE);
            btnConcluirTeste.setVisibility(View.VISIBLE);
        }


        //CRIANDO INTENT
        final Intent proxQuestaoIntent = new Intent(MenuTestesActivity.this, MenuTestesActivity.class);
        proxQuestaoIntent.putExtra("idQuestao", idProxQuestao);
        //TODO adicionar asquestões certas/erradas do usuario

        testeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // exibir questao
                txtPergunta.setText(dataSnapshot.child("descricaoQuestao").getValue(String.class));

                //exibir respostas
                int qtdRespostas = (int) (dataSnapshot.child("respostas").getChildrenCount());

                for (int i = 1; i <= qtdRespostas; i++) {
                    arrayRespostas.add(dataSnapshot.child("respostas").child("resposta" + i).getValue(String.class));

                    if (i == 1) {
                        rbResposta1.setText(arrayRespostas.get(i - 1));
                    }
                    if (i == 2) {
                        rbResposta2.setText(arrayRespostas.get(i - 1));
                    }
                    if (i == 3) {
                        rbResposta3.setVisibility(View.VISIBLE);
                        rbResposta3.setText(arrayRespostas.get(i - 1));
                    }
                }

                //TODO FAZER UM CASE pra cada numero de respostas possivveis (até 5)
                if (qtdRespostas < 3) {
                    rbResposta3.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnProxQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuTestesActivity.this.startActivity(proxQuestaoIntent);
            }
        });

        btnConcluirTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Terminar teste
            }
        });

    }
}
