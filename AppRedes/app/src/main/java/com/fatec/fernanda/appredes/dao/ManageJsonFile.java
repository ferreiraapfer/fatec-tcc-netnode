package com.fatec.fernanda.appredes.Dao;

import android.util.JsonReader;
import android.util.JsonWriter;

import com.fatec.fernanda.appredes.Domain.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * Created by Fernanda on 01/09/2016.
 */

public class ManageJsonFile {

    //  ESCRITA

    public void writeJsonStream(OutputStream out, Usuario usuario) throws IOException{
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeUsuario(writer, usuario);
        writer.close();
    }

    public void writeUsuario(JsonWriter writer, Usuario usuario) throws IOException{
        writer.beginObject();
        writer.name("email").value(usuario.getEmail());
        writer.name("nome").value(usuario.getNome());
        writer.name("pontuacao").value(usuario.getPontuacao());
        writer.endObject();
    }


    // LEITURA

    public Usuario readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try {
            return readUsuario(reader);
        } finally {
            reader.close();
        }

    }

    public Usuario readUsuario(JsonReader reader) throws IOException {
        String emailUsuario = null;
        String nomeUsuario = null;
        int pontuacaoUsuario = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String idObjeto = reader.nextName();
            if (idObjeto.equals("nome")) {
                nomeUsuario = reader.nextString();
            } else if (idObjeto.equals("email")) {
                emailUsuario = reader.nextString();
            } else if (idObjeto.equals("pontuacao")) {
                pontuacaoUsuario = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Usuario novoUsuario = new Usuario(emailUsuario, nomeUsuario);
        novoUsuario.setPontuacao(pontuacaoUsuario);

        return novoUsuario;
    }


}
