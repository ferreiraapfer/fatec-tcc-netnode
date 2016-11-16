package com.fatec.fernanda.appredes.tasks.impl;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Fernanda on 16/11/2016.
 */

public abstract class AbstractAsyncTask<E, S> extends AsyncTask<E, String, S> {

    private AsyncTaskListener listener;
    private String mensagem;

    public void setAsyncTaskLisener(AsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onPreExecute();
    }

    @Override
    protected S doInBackground(E... es) {
        E classe = es[0];

        try {
            return executeService(classe);
        } catch (Exception ex) {
            mensagem = ex.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(S s) {
        if (mensagem != null) {
            listener.onError(mensagem);
        } else {
            listener.onComplete(s);
            ;
        }
    }

    protected abstract S executeService(E entrada) throws IOException, InterruptedException;
}
