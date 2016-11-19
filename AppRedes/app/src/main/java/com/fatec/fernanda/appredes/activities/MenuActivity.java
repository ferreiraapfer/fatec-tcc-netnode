package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.SistemaActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private TextView txtConteudos;
    private TextView txtTestes;
    private TextView txtRevisoes;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtConteudos = (TextView) findViewById(R.id.conteudosLink);
        txtTestes = (TextView) findViewById(R.id.testesLink);
        txtRevisoes = (TextView) findViewById(R.id.revisoesLink);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            MenuActivity.this.startActivity(new Intent(this, LoginActivity.class));
        }

        txtConteudos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuConteudosIntent = new Intent(MenuActivity.this, MenuConteudosActivity.class);
                MenuActivity.this.startActivity(menuConteudosIntent);
            }
        });

        txtTestes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuTestesIntent = new Intent(MenuActivity.this, MenuTestesActivity.class);
                menuTestesIntent.putExtra("idQuestao", 1);
                MenuActivity.this.startActivity(menuTestesIntent);


            }
        });

        txtRevisoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuRevisoesIntent = new Intent(MenuActivity.this, MenuRevisoesActivity.class);
                MenuActivity.this.startActivity(menuRevisoesIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu, this adds items to the action baf if it is present
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button,
        so long as you specify activity in AndroidManifest.xml
        */

        int id = item.getItemId();

        switch (id) {
            case R.id.perfilLink:
                Intent perfilIntent = new Intent(MenuActivity.this, PerfilActivity.class);
                MenuActivity.this.startActivity(perfilIntent);
                return true;
            case R.id.tutorialLink:
                Intent tutorialIntent = new Intent(MenuActivity.this, TutorialActivity.class);
                MenuActivity.this.startActivity(tutorialIntent);
                return true;
            case R.id.sistemaLink:
                Intent sistemaIntent = new Intent(MenuActivity.this, SistemaActivity.class);
                MenuActivity.this.startActivity(sistemaIntent);
                return true;
            case R.id.logoutLink:
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
