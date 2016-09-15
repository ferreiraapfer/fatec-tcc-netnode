package com.fatec.fernanda.appredes.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.Dao.FirebaseHelper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, helper.retrieve());
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
/*

        db = FirebaseDatabase.getInstance().getReference();

        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                conteudosArrayList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Conteudo c = new Conteudo();
                    c.setTitulo(data.getValue(Conteudo.class).getTitulo());
                    c.setSubtitulo(data.getValue(Conteudo.class).getSubtitulo());

                    conteudosArrayList.add(c);

                }

                if (conteudosArrayList.size() > 0) {
                    adapter = new ArrayAdapter(MenuConteudosActivity.this, android.R.layout.two_line_list_item, conteudosArrayList);
                    conteudosList.setAdapter(adapter);

                } else {
                    Toast.makeText(MenuConteudosActivity.this, "Array menor que zero", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*


        mFirebaseRef = new Firebase("https://appredes-a8895.firebaseio.com/tb_conteudo");


        mFirebaseRef.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListAdapter = new FirebaseListAdapter<Conteudo>(MenuConteudosActivity.this, Conteudo.class, android.R.layout.two_line_list_item, mFirebaseRef) {
                    @Override
                    protected void populateView(View v, Conteudo model, int position) {
                        ((TextView) v.findViewById(android.R.id.text1)).setText(model.getTitulo());
                        ((TextView) v.findViewById(android.R.id.text2)).setText(model.getSubtitulo());
                    }
                };

                conteudosList.setAdapter(mListAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                String message = "Server error. Refresh page";
                Toast.makeText(MenuConteudosActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });
*/
    }

}
