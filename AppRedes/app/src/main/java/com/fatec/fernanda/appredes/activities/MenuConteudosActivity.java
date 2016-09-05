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

    private Firebase mFirebaseRef;
    FirebaseListAdapter<Conteudo> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conteudos);

        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase("https://appredes-a8895.firebaseio.com/tb_conteudo");

        final ListView listView = (ListView) this.findViewById(android.R.id.list);

        mListAdapter = new FirebaseListAdapter<Conteudo>(this, Conteudo.class, android.R.layout.two_line_list_item, mFirebaseRef) {
            @Override
            protected void populateView(View v, Conteudo model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getTitulo());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getSubtitulo());
            }
        };
        listView.setAdapter(mListAdapter);

    }
}
