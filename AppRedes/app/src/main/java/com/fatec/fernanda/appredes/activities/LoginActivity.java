package com.fatec.fernanda.appredes.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogin;
    private TextView txtCadastroLink;
    private TextView txtNovaSenha;

    private ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseUser usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtCadastroLink = (TextView) findViewById(R.id.txtCadastroLink);
        txtNovaSenha = (TextView) findViewById(R.id.txtNovaSenha);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        txtCadastroLink.setOnClickListener(this);
        txtNovaSenha.setOnClickListener(this);

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
                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    usuario = firebaseAuth.getCurrentUser();
                    if (usuario.isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Email de Verificação")
                                .setMessage("Conta não verificada. Cheque seu provedor de email para verificar o cadastro")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }


                } else {
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            if (e.getMessage() == "The password is invalid or the user does not have a password.") {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Senha Inválida")
                                        .setMessage("Caso tenha esquecido sua senha, solicite uma nova")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                return;
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else if(e.getMessage() == "There is no user record corresponding to this identifier. The user may have been deleted."){
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Conta Inexistente")
                                        .setMessage("Esse email não está cadastrado em uma conta.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                return;
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }

                        }
                    });

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

        if (view == txtNovaSenha) {
            novaSenha();
        }

    }

    private void novaSenha() {

        String email = edtEmail.getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Digite um email", Toast.LENGTH_LONG).show();
        } else {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("NOVA SENHA", "Email sent.");
                            }
                        }
                    });
        }
    }
}
