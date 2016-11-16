package com.fatec.fernanda.appredes.fragments;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Fernanda on 12/11/2016.
 */

public interface OnGetDataListener {

    public void onStart();

    public void onSuccess(DataSnapshot data);

    public void onFailed(DatabaseError databaseError);
}
