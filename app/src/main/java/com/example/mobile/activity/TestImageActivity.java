package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.mobile.R;
import com.example.mobile.database.sqlite.NoteDAO;

import java.util.Date;

public class TestImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.girl);
        NoteDAO noteDAO= new NoteDAO(this);
        noteDAO.insertImage(1,icon,new Date());
        Bitmap bitmap=noteDAO.getImage(1);
        if(bitmap instanceof Bitmap){
            Log.e("final","ok");
        }
    }
}