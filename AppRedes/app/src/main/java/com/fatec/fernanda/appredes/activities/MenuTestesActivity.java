package com.fatec.fernanda.appredes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuTestesActivity extends AppCompatActivity {

    TextView txtPergunta;
    ImageView imgIlustracao;

    RadioGroup radioGroup;
    RadioButton rbResposta1;
    RadioButton rbResposta2;
    RadioButton rbResposta3;

    DatabaseReference testeRef;
    DatabaseReference respostasRef;


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

        testeRef = FirebaseDatabase.getInstance().getReference().child("questoes").child("questao1");
        respostasRef = testeRef.child("respostas");

        /*
        questao1
            conteudo1 :true
             descricaoQuestao : ...
             explicaçãoResposta: ...
             respostaCerta: resposta1
             respostas
                resposta1: Sim
                resposta2 : Não
         */

        testeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // exibir questao
                txtPergunta.setText(dataSnapshot.child("descricaoQuestao").getValue(String.class));

                //exibir respostas
                System.out.println(dataSnapshot.child("respostas").toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
