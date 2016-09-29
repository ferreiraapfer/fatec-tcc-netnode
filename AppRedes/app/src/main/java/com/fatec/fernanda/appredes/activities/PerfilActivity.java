package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.adapters.ConteudoConcluidoAdapter;
import com.fatec.fernanda.appredes.models.ConteudoConcluido;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class PerfilActivity extends AppCompatActivity {

    private TextView emailUsuario;
    private TextView nomeUsuario;
    private ProgressBar progresso;
    private TextView conteudosConcluidos;
    private TextView progressoTexto;


    FirebaseAuth firebaseAuth;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        /*

        ManageJsonFile manageJsonFile = new ManageJsonFile();


        InputStream in;
        try {
            in = getApplicationContext().openFileInput("Usuario.json");
            Usuario user = manageJsonFile.readJsonStream(in);

            emailUsuario.setText(user.getEmail());
            nomeUsuario.setText(user.getNome());
            progresso.setProgress(user.getPontuacao());
            progressoTexto.setText(String.valueOf(user.getPontuacao())+"%");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/
        //TODO arrumar pontuação texto, maximo e barra

        initConteudosConcluidosListView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        emailUsuario = (TextView) findViewById(R.id.email_usuario);
        nomeUsuario = (TextView) findViewById(R.id.nome_usuario);
        progresso = (ProgressBar) findViewById(R.id.progresso_barra);
        progressoTexto = (TextView) findViewById(R.id.progresso);
        conteudosConcluidos = (TextView) findViewById(R.id.textConteudosConcluidos);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();


        emailUsuario.setText(user.getEmail());

        if(user.getDisplayName() != null){
            nomeUsuario.setText(user.getDisplayName());
        } else  {
            Toast.makeText(this, "Sem nome", Toast.LENGTH_LONG);
        }


        mRef = new Firebase("https://appredes-a8895.firebaseio.com/");

        /*
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                conteudosConcluidos.setText(text);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        */
    }


    private ArrayList<ConteudoConcluido> createConteudosConcluidosList() {
        ArrayList<ConteudoConcluido> list = new ArrayList<ConteudoConcluido>();

        for (int i = 1; i <= 5; i++) {
            list.add(ConteudoConcluido.novaInstancia(i));
        }

        return list;
    }

    private void initConteudosConcluidosListView() {
        ListView listView = (ListView) findViewById(R.id.advanced_list_view);

        if (listView == null) {
            return;
        }

        ArrayList<ConteudoConcluido> list = createConteudosConcluidosList();

        ConteudoConcluidoAdapter adapter = new ConteudoConcluidoAdapter(this, R.layout.lista_conteudos_concluidos_item, list);
        listView.setAdapter(adapter);
    }


}
