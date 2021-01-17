package com.example.mobile.database.sqlite;

import android.app.Activity;
import android.util.Log;

import com.example.mobile.model.InfoShare;

import java.util.List;

public class NoteSharedDAO extends  NoteDAO {
    public NoteSharedDAO(Activity activity) {
        super(activity);
    }

    public void shared(InfoShare i, List<Integer> lstShared) {
        Log.e("shared name", i.getName());
        Log.e("shared email", i.getEmail());
        Log.e("lstShared", "size= "+lstShared.size());
    }
}
