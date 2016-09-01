package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.dao.ManageJsonFile;
import com.fatec.fernanda.appredes.domain.Usuario;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText edtNome = (EditText) findViewById(R.id.edtNome);
        final EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        final Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            //TODO Método Autenticar Cadastro
            //TODO Exibir que a senha deve ter pelo menso 5 caracteres

            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Digite um email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (edtNome.getText().toString().isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Digite um nome", Toast.LENGTH_LONG).show();
                    return;
                }

                Usuario user = new Usuario(edtEmail.getText().toString(), edtNome.getText().toString());

                //Salvando dados num arquivo

                ManageJsonFile manageJsonFile = new ManageJsonFile();

                File arquivoUsuario = new File(getFilesDir(), "Usuario.json");

                try {
                    FileOutputStream out = new FileOutputStream(arquivoUsuario);
                    manageJsonFile.writeJsonStream(out, user);

                    Toast.makeText(getBaseContext(), "Texto gravado com sucesso.", Toast.LENGTH_SHORT).show();

                    Intent menuIntent = new Intent(CadastroActivity.this, MenuActivity.class);
                    CadastroActivity.this.startActivity(menuIntent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
