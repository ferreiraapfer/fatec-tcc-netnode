package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.Topico;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TopicoActivity extends AppCompatActivity {

    Topico topico;
    String tituloTopico;
    int idTopico;

    TextView txtTituloTopico;
    TextView txtConteudoTopico;
    WebView wvConteudo;

    DatabaseReference topicoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topico);

        txtTituloTopico = (TextView) findViewById(R.id.txtTituloTopico);
        txtConteudoTopico = (TextView) findViewById(R.id.txtConteudoTopico);
        wvConteudo = (WebView) findViewById(R.id.webConteudo);

        topicoRef = FirebaseDatabase.getInstance().getReference().child("topicos");

        //RECEBENDO O ID/NOME DO TOPICO
        Intent originIntent = getIntent();
        idTopico = originIntent.getExtras().getInt("idTopico");
        tituloTopico = originIntent.getExtras().getString("tituloTopico");

        topico = new Topico();
        topico.setId(idTopico);
        topico.setTitulo(tituloTopico);

        //POSICIONA PARA A ÁREA DO TOPICO NO BANCO
        topicoRef = topicoRef.child("topico"+idTopico).child("texto");

        topicoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtConteudoTopico.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //TODO tela "rolável"

        txtTituloTopico.setText(topico.getTitulo());








    }
}
