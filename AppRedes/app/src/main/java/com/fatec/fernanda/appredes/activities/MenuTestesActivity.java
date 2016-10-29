package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.dao.FirebaseHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuTestesActivity extends AppCompatActivity {

    ArrayAdapter<String> adapterConteudosTestes;
    ListView conteudosList;
    DatabaseReference db;
    FirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_testes);

        conteudosList = (ListView) findViewById(R.id.menuConteudosTestes);

        //SETUP FIREBASE TODO contar os topicos/conteudos concluidos
        //TODO contar os testes já feitos
        db = FirebaseDatabase.getInstance().getReference("conteudos");
        helper = new FirebaseHelper(db);


        //ADAPTER
        adapterConteudosTestes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, (List<String>) helper.retrieve());
        conteudosList.setAdapter(adapterConteudosTestes);


        conteudosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent testeIntent = new Intent(MenuTestesActivity.this, TesteActivity.class);

                //Enviando id do Conteudo para a nova Activity
                testeIntent.putExtra("idConteudo", i+1);

                //TODO Mandar no intent o numero da primeira questão do teste

                MenuTestesActivity.this.startActivity(testeIntent);
            }
        });
    }
}
