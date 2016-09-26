package com.fatec.fernanda.appredes.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private TextView txtLoginLink;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

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

        if(firebaseAuth.getCurrentUser() != null){
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
                    FirebaseUser usuario = firebaseAuth.getCurrentUser();
                    Log.e("Usuário cadastrado", usuario.getDisplayName() + " " + usuario.getEmail() + " ");
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();

                    usuario.updateProfile(profileUpdates);

                    Log.e("Usuário modificado", usuario.getDisplayName() + " " + usuario.getEmail() + " ");

                    if(usuario.getEmail() == email && usuario.getDisplayName() == nome){
                        Log.e("Validação", "Validação nome e email conferem");
                    }

                    progressDialog.dismiss();
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                } else {
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
