package com.fatec.fernanda.appredes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class SistemaActivity extends AppCompatActivity {

    TextView txtIntro;
    TextView txtNome;
    TextView txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistema);

        txtIntro = (TextView) findViewById(R.id.txtIntro);
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        txtIntro.setText("NetNode é um aplicativo para aprender sobre Redes. Qualquer conteúdo presente nesse aplicativo é adaptado de livros e apostilas." +
                "\nCaso queira deixar algum feedback sobre sugestões, ideias, reclamações ou bugs, por favor, envie um email para:");

        txtNome.setText("Fernanda Aparecida Ferreira");
        txtEmail.setText(Html.fromHtml("<a href=\"mailto:ferreiraaparecidaf@gmail.com\">Enviar Feedback</a>"));
        txtEmail.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
