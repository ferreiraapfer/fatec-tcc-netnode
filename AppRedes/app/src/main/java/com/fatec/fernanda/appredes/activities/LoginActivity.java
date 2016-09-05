package com.fatec.fernanda.appredes.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogin;
    private TextView txtCadastroLink;

    private ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtCadastroLink = (TextView) findViewById(R.id.txtCadastroLink);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //Usuário logado
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        }

        btnLogin.setOnClickListener(this);
        txtCadastroLink.setOnClickListener(this);

    }

    private void loginUsuario() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Digite um email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Digite uma senha", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            loginUsuario();
        }

        if (view == txtCadastroLink) {
            finish();
            startActivity(new Intent(this, CadastroActivity.class));
        }

    }
}
