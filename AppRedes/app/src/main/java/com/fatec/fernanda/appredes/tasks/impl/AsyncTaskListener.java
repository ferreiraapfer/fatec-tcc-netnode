package com.fatec.fernanda.appredes.tasks.impl;

/**
 * Created by Fernanda on 16/11/2016.
 */

public interface AsyncTaskListener {

    void onPreExecute();

    <T> void onComplete(T obj);

    void onError(String errorMsg);
}
