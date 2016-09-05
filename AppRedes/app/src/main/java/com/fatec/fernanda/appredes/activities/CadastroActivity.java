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
import com.google.firebase.auth.UserProfileChangeRequest;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        EditText edtNome = (EditText) findViewById(R.id.edtNome);
        EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        EditText edtSenha = (EditText) findViewById(R.id.edtSenha);

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        TextView txtLoginLink = (TextView) findViewById(R.id.txtLoginLink);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else  {
            edt
        }

    }

}
