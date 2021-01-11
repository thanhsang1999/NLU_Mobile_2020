package com.example.mobile;

import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;

public interface IFragmentCanAddNote  {
    public <T> void startActivity(android.content.Context context, Class<T> classActivity);
    public void updateApdater(Notebook notebook);

}
