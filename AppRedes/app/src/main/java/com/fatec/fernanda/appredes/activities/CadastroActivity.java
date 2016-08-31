package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.dao.CustomSQLiteOpenHelper;
import com.fatec.fernanda.appredes.dao.UsuarioDAO;
import com.fatec.fernanda.appredes.domain.Usuario;

public class CadastroActivity extends AppCompatActivity {

    UsuarioDAO usrDAO = new UsuarioDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText edtNome = (EditText) findViewById(R.id.edtNome);
        final EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        final EditText edtSenha = (EditText) findViewById(R.id.edtSenha);
        final Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        final Button btnLoginLink = (Button) findViewById(R.id.btnLogin);

        btnLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                CadastroActivity.this.startActivity(loginIntent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            //TODO Método Autenticar Cadastro
            //TODO Exibir que a senha deve ter pelo menso 5 caracteres

            @Override
            public void onClick(View view) {
                if(edtEmail.getText().toString().isEmpty()){
                    Toast.makeText(CadastroActivity.this, "Digite um email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(edtNome.getText().toString().isEmpty()){
                    Toast.makeText(CadastroActivity.this, "Digite um nome", Toast.LENGTH_LONG).show();
                    return;
                }
                if(edtSenha.getText().toString().isEmpty() || edtSenha.getText().toString().length() < 5){
                    Toast.makeText(CadastroActivity.this, "Digite uma senha válida", Toast.LENGTH_LONG).show();
                    return;
                }

                Usuario user = new Usuario(edtEmail.getText().toString(), edtNome.getText().toString(), edtSenha.getText().toString());

                if(usrDAO.insertUsuario(user, CadastroActivity.this)){
                    Intent loginIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                    CadastroActivity.this.startActivity(loginIntent);
                } else{
                    Toast.makeText(CadastroActivity.this, "Erro no cadastro", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
