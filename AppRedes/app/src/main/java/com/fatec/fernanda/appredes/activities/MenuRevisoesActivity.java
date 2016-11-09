package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.RevisaoActivity;
import com.fatec.fernanda.appredes.interfaces.MenuRevisaoChildView;
import com.fatec.fernanda.appredes.models.Conteudo;
import com.fatec.fernanda.appredes.models.DataWrapper;
import com.fatec.fernanda.appredes.models.Teste;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuRevisoesActivity extends AppCompatActivity {


    ArrayList<MenuRevisaoChildView> childs;
    ArrayList<Conteudo> conteudos;

    DatabaseReference conteudosConcluidosRef;
    DatabaseReference conteudosRef;
    DatabaseReference questoesConteudo;

    LinearLayout linearLayout;
    Button btnRealizarRevisao;

    int numQuestoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_revisoes);

        linearLayout = (LinearLayout) findViewById(R.id.linLayoutMenuRevisoes);
        btnRealizarRevisao = (Button) findViewById(R.id.btnRealizarRevisao);

        conteudos = new ArrayList<>();
        childs = new ArrayList<>();

        numQuestoes = 0;

        //SETUP FIREBASE
        conteudosRef = FirebaseDatabase.getInstance().getReference().child("conteudos");
        conteudosConcluidosRef = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("conteudosConcluidos");
        questoesConteudo = FirebaseDatabase.getInstance().getReference().child("questoes");

        //pegar ids dos conteudos concluidos
        conteudosConcluidosRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(double.class) >= 5) {

                    final Conteudo novoConteudo = new Conteudo();
                    novoConteudo.setId(dataSnapshot.getKey().substring(8));

                    conteudosRef.child(dataSnapshot.getKey()).child("titulo").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            novoConteudo.setTitulo(dataSnapshot.getValue(String.class));

                            conteudos.add(novoConteudo);

                            final MenuRevisaoChildView child = new MenuRevisaoChildView(MenuRevisoesActivity.this);
                            child.setCheckedTextView(novoConteudo.getTitulo());
                            child.setIdConteudo(novoConteudo.getId());

                            child.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (child.isChecked()) {
                                        child.setUnchecked();
                                    } else {
                                        child.setChecked();
                                    }
                                }
                            });


                            int flag = 0;

                            //PEGANDO QUESTOES

                            questoesConteudo.child("conteudo" + novoConteudo.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int n = (int) dataSnapshot.getChildrenCount();

                                    child.setNumQuestoes(n);

                                    linearLayout.addView(child);
                                    childs.add(child);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

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
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnRealizarRevisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> idConteudos = new ArrayList<>();

                numQuestoes = 0;

                for (MenuRevisaoChildView child : childs) {
                    if (child.isChecked()) {
                        idConteudos.add("conteudo" + child.getIdConteudo());
                        numQuestoes = numQuestoes + child.getNumQuestoes();

                    }
                }

                Intent revisaoIntent = new Intent(MenuRevisoesActivity.this, RevisaoActivity.class);

                //Enviando id dos Conteudos para a nova Activity
                revisaoIntent.putExtra("idConteudos", idConteudos);
                revisaoIntent.putExtra("numQuestoesConteudos", numQuestoes);

                MenuRevisoesActivity.this.startActivity(revisaoIntent);

            }
        });

    }

    /*
                        questoes.runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                questoes.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        numQuestoes[0] = numQuestoes[0] + (int) dataSnapshot.getChildrenCount();
                                        questoes.removeEventListener(this);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                return null;
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {



                            }
                        }); */

    /*
    * TODO menu selecionável dos conteúdos concluídos
    * Intent com os IDs dos conteúdos.
    * */
}
