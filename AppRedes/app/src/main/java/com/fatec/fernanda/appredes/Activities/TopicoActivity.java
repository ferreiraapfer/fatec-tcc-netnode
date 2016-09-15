package com.fatec.fernanda.appredes.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fatec.fernanda.appredes.R;

public class TopicoActivity extends AppCompatActivity {

    int idTopico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topico);


        //RECEBENDO O ID DO TOPICO
        Intent originIntent = getIntent();
        idTopico = originIntent.getExtras().getInt("idTopico");
    }
}
