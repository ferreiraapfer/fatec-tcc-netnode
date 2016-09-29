package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;

public class TopicoActivity extends AppCompatActivity {

    String topico;
    TextView txtTituloTopico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topico);

        txtTituloTopico = (TextView) findViewById(R.id.txtTituloTopico);

        //RECEBENDO O ID/NOME DO TOPICO
        Intent originIntent = getIntent();
        topico = originIntent.getExtras().getString("topico");
        txtTituloTopico.setText(topico);


    }
}
