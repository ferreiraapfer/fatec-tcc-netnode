package com.fatec.fernanda.appredes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;

public class TutorialActivity extends AppCompatActivity {

    TextView txtIntro;

    TextView txtConteudos;
    TextView txtTestes;
    TextView txtRevisoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        txtIntro = (TextView) findViewById(R.id.txtIntro);

        txtConteudos = (TextView) findViewById(R.id.txtConteudos);
        txtTestes = (TextView) findViewById(R.id.txtTestes);
        txtRevisoes = (TextView) findViewById(R.id.txtRevisoes);


        txtIntro.setText("NetNode é um aplicativo para aprender os conceitos básicos sobre Redes de Computadores.");

        txtConteudos.setText("A área de Conteúdos exibe os conteúdos principais sobre Redes de Computadores. " +
                "Cada conteúdo é dividido em um tópico, que possui a explicação sobre esse tópico.");

        txtTestes.setText("A área de Testes exibe os testes sobre os conteúdos. Cada teste possui respostas múltipla-escolha, " +
                "e uma nota maior que 5 liberará um novo conteúdo de estudo, além de ganhar pontos. Cada questão do teste " +
                "também possui uma explicação, que será exibida na conclusão do teste. Após a realização de um teste, com nota satisfatória, " +
                "um teste sobre o mesmo conteúdo não poderá mais ser realizado");

        txtRevisoes.setText("As revisões são testes sobre um ou mais conteúdos concluídos. Ela não acrescenda pontos, e " +
                "nem libera novos conteúdos.");
    }
}
