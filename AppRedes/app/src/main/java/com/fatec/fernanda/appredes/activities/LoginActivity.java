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

public class LoginActivity extends AppCompatActivity {

    UsuarioDAO usrDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usrDAO = new UsuarioDAO(this);

        final EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        final EditText edtSenha = (EditText) findViewById(R.id.edtSenha);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Button btnCadastrarLink = (Button) findViewById(R.id.btnCadastrar);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] args = new String[]{edtEmail.getText().toString(),
                        edtSenha.getText().toString()};

                Usuario user = usrDAO.login(args, LoginActivity.this);

                if (user == null) {
                    //Toast.makeText(LoginActivity.this, "Erro no login", Toast.LENGTH_SHORT).show();
                } else {
                    Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);

                    menuIntent.putExtra("usuario", user);

                    LoginActivity.this.startActivity(menuIntent);
                }

            }
        });


        btnCadastrarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastroIntent = new Intent(LoginActivity.this, CadastroActivity.class);
                LoginActivity.this.startActivity(cadastroIntent);
            }
        });
    }
}
