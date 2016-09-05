package com.fatec.fernanda.appredes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.domain.Conteudo;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

public class MenuConteudosActivity extends AppCompatActivity {

    FirebaseListAdapter<Conteudo> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conteudos);

        ListView conteudosView = (ListView) findViewById(R.id.list);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://appredes-a8895.firebaseio.com/tb_conteudo");


        mAdapter = new FirebaseListAdapter<Conteudo>(this, Conteudo.class, android.R.layout.two_line_list_item, ref) {
            @Override
            protected void populateView(View v, Conteudo conteudo, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(conteudo.getId() + conteudo.getTitulo());
                ((TextView)v.findViewById(android.R.id.text2)).setText(conteudo.getSubtitulo());
            }
        };
        conteudosView.setAdapter(mAdapter);

    }
}
