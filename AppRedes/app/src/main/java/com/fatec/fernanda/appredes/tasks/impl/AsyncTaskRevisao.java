package com.fatec.fernanda.appredes.tasks.impl;

import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.service.RevisaoService;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Fernanda on 16/11/2016.
 */

public class AsyncTaskRevisao extends AbstractAsyncTask<ArrayList, ArrayList<Questao>> {
    @Override
    protected ArrayList<Questao> executeService(ArrayList ids) throws IOException, InterruptedException {
        RevisaoService service = new RevisaoService();
        return null;
    }
}
