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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private TextView txtLoginLink;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUsuarioRef = mRootRef.child("usuarios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        txtLoginLink = (TextView) findViewById(R.id.txtLoginLink);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //Usuário logado
            startActivity(new Intent(CadastroActivity.this, MenuActivity.class));
        }

        btnCadastrar.setOnClickListener(this);
        txtLoginLink.setOnClickListener(this);

    }

    private void cadastrarUsuario() {
        final String nome = edtNome.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String senha = edtSenha.getText().toString().trim();


        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Digite um nome", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Digite um email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Digite uma senha", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Cadastrando usuário...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();


                    mUsuarioRef.child(user.getUid()).child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                mUsuarioRef.child(user.getUid()).child("nome").setValue(nome);
                                mUsuarioRef.child(user.getUid()).child("progresso").setValue(0);

                                progressDialog.dismiss();

                                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(CadastroActivity.this, "Erro no cadastrado ao banco de dados. Por favor, tente novamente", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(CadastroActivity.this, "Erro no cadastrado. Por favor, tente novamente", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnCadastrar) {
            cadastrarUsuario();
        }

        if (view == txtLoginLink) {
            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
        }
    }
}
