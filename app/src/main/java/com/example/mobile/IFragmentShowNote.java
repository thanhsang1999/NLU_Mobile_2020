package com.example.mobile;

import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;

import java.util.List;

public interface IFragmentShowNote {
    public  void startActivityNewNote(android.content.Context context, int idNotebook, int index);
    public  void startActivityShareNote();
    public void updateApdater(Notebook notebook,int index);

}
