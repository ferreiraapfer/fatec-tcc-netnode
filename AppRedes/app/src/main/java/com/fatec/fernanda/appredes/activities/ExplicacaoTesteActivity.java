package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.Teste;

import java.util.ArrayList;

public class ExplicacaoTesteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacao_teste);

        Intent originIntent = getIntent();
        int idQuestao = originIntent.getExtras().getInt("idQuestao");
        int idConteudo = originIntent.getExtras().getInt("idConteudo");
        int numQuestoes = originIntent.getExtras().getInt("numQuestoes");
        ArrayList<Teste> arrayMeuTeste = (ArrayList<Teste>) originIntent.getExtras().getSerializable("arrayMeuTeste");

        System.out.println("ARRAY TEM ELEMENTOS: " + (arrayMeuTeste.size() + 1));


    }


    /*
    * TODO Pegar array de perguntas, array de respostas e array de respostas selecionadas
    * TODO Calcular nota do teste e exibir
    * TODO Pegar explicações das questões selecionadas
    * TODO Exibir
    * */
}
