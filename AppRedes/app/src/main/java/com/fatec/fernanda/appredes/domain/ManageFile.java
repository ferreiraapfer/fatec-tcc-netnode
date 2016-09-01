package com.fatec.fernanda.appredes.domain;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Fernanda on 31/08/2016.
 */

public class ManageFile {

    private static final String TAG = "ManageFile";
    private Context context;

    public ManageFile(Context context) {
        this.context = context;
    }

    public boolean WriteFile(String fileName, String[] content) {
        try {
            // Abre o arquivo para escrita ou cria se não existir
            FileOutputStream out = context.openFileOutput(fileName + ".txt",
                    Context.MODE_APPEND);

            for (int i = 0; i < content.length; i++) {
                out.write(content[i].getBytes());
                out.write("\n".getBytes());
                out.flush();
                out.close();
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public String ReadFile(String fileName) throws IOException {
        File file = context.getFilesDir();
        File textfile = new File(file + "/" + fileName + ".txt");

        FileInputStream input = context.openFileInput(fileName + ".txt");
        byte[] buffer = new byte[(int) textfile.length()];

        input.read(buffer);

        return new String(buffer);
    }



    /*******************/

    public void writeToFile(String[] data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(String.valueOf(data));
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}
