package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.dao.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MenuConteudosActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView conteudosList;
    DatabaseReference db;
    FirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conteudos);

        conteudosList = (ListView) findViewById(R.id.menuConteudos);

        //SETUP FIREBASE
        db = FirebaseDatabase.getInstance().getReference("tb_conteudo");
        helper = new FirebaseHelper(db);


        //ADAPTER
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, (List<String>) helper.retrieve());
        conteudosList.setAdapter(adapter);


        conteudosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent menuTopicosIntent = new Intent(MenuConteudosActivity.this, MenuTopicosActivity.class);

                //Enviando id do Tópico para a nova Activity
                menuTopicosIntent.putExtra("idConteudo", i);

                MenuConteudosActivity.this.startActivity(menuTopicosIntent);
            }
        });
    }

}
