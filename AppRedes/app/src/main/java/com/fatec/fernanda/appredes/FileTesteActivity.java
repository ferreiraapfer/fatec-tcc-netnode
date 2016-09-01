package com.fatec.fernanda.appredes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.fatec.fernanda.appredes.domain.ManageFile;
import java.io.IOException;

public class FileTesteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_teste);

        final TextView textRead = (TextView) findViewById(R.id.textRead);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button btnRead = (Button) findViewById(R.id.btnRead);
        final Button btnWrite = (Button) findViewById(R.id.btnWrite);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Faz a leitura do arquivo
                ManageFile fileread = new ManageFile(getBaseContext());
                try {
                    textRead.setText(fileread.ReadFile("usuario"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManageFile filewrite = new ManageFile(getBaseContext());

                String[] argsUsuario = {editText.getText().toString()};



                // Avisa o usuário se a gravação foi bem sucedida
                if(filewrite.WriteFile("usuario", argsUsuario)
                        == true){
                    Toast.makeText(getBaseContext(),
                            "Texto gravado com sucesso.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(),
                            "Não foi possível escrever o texto.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
