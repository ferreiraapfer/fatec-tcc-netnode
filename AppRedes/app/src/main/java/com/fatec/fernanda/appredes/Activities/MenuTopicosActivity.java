package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fatec.fernanda.appredes.dao.FirebaseHelper;
import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.Topico;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MenuTopicosActivity extends AppCompatActivity {

    ArrayAdapter<Topico> adapter;
    ListView topicosList;
    DatabaseReference db;
    FirebaseHelper helper;
    int idConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_topicos);

        //RECEBENDO O ID DO CONTEUDO
        Intent originIntent = getIntent();
        idConteudo = originIntent.getExtras().getInt("idConteudo");

        topicosList = (ListView) findViewById(R.id.lstMenuTopicos);

        //SETUP FIREBASE
        db = FirebaseDatabase.getInstance().getReference("tb_topico");
        helper = new FirebaseHelper(db);


        //TODO RETRIEVE DO HELPER PARA TOPICOS A PARTIR DO ID DO CONTEUDO SELECIONADO

        //ADAPTER
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,(List<Topico>) helper.getTopicosConteudo(idConteudo));
        topicosList.setAdapter(adapter);


        topicosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent topicoIntent = new Intent(MenuTopicosActivity.this, TopicoActivity.class);

                topicoIntent.putExtra("idTopico", i);

                MenuTopicosActivity.this.startActivity(topicoIntent);


            }
        });


    }
}
