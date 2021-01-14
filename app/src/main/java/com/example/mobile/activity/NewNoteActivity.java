package com.example.mobile.activity;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;
import com.example.mobile.adapter.NewNoteAdapter;
import com.example.mobile.database.sqlite.NoteDAO;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;
import com.example.mobile.utils.ExactThreadHelper;
import com.example.mobile.utils.MyNotificationHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class NewNoteActivity extends AppCompatActivity {
    EditText editTextTitle,editTextContent;
    ScrollView Mainlayout;
    ConstraintLayout  contentLayout;
    NoteDAO sqLite;

    private int mYear, mMonth, mDay, mHour, mMinute;
    Notebook notebook;
    FloatingActionButton fabMain,fabCamera,fabFile;
    float translationY = 100f;
    boolean isMenuOpen = false;
    OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    RecyclerView recyclerView;
    NewNoteAdapter newNoteAdapter;

    int idPackage=0;
    int idNotebook=0;
    int index=-1;
    public int PERM_CODE = 111;
    public int REQUEST_CODE_CAMERA = 112;
    public int PERM_CODE_GALLERY = 222;
    public int REQUEST_CODE_GALLERY = 223;

    String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        idPackage=getIntent().getExtras().getInt("idPackage");
        idNotebook=getIntent().getExtras().getInt("idNotebook");
        index=getIntent().getExtras().getInt("index");
        Log.e("new note","index="+index);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more_menu));
        init();
        FabOnClick();
        //datatest images

//        images.add(R.drawable.cat1);
//        images.add(R.drawable.cat2);
//        images.add(R.drawable.cat3);
//        images.add(R.drawable.cat4);

        // RecyclerView
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(NewNoteActivity.this,DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(NewNoteActivity.this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewNoteActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        newNoteAdapter = new NewNoteAdapter(NewNoteActivity.this,notebook.getImages());
        recyclerView.setAdapter(newNoteAdapter);
    }

    private void init() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        Mainlayout = findViewById(R.id.mainLayout);
        contentLayout = findViewById(R.id.contentLayout);
        editTextContent.requestFocus();
        sqLite  = new NoteDAO(this);
        if(idNotebook==0)
            notebook= new Notebook();
        else {
            notebook = sqLite.getNotebook(idNotebook);
            editTextTitle.setText(notebook.getTitle());
            editTextContent.setText(notebook.getContent());
            Log.e("content",notebook.getContent());
        }

        // set fab
        fabMain =  findViewById(R.id.fabMain);
        fabFile =  findViewById(R.id.fabFile);
        fabCamera =  findViewById(R.id.fabCamera);

        fabFile.setAlpha(0f);
        fabCamera.setAlpha(0f);

        fabFile.setTranslationY(translationY);
        fabCamera.setTranslationY(translationY);
        // RecyclerView
        recyclerView = findViewById(R.id.recyclerViewImage);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                String title = editTextTitle.getText().toString();
                String textContent = editTextContent.getText().toString();


                notebook.setTitle(title);
                notebook.setContent(textContent);
                notebook.setDateEdit(new Date());

                if (!textContent.equals("")){
                    if(idNotebook==0){
                        notebook=sqLite.insertNotebook(notebook,idPackage, true);
                        sqLite.sync();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("notebook",notebook);
                        Log.e("new notea","imageC"+notebook.getImages().size());
                        returnIntent.putExtra("index",index);
                        setResult(Activity.RESULT_OK,returnIntent);
                    }else{
                        int rs=sqLite.updateNotebook(notebook,idPackage);

                        if(rs==1){
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("notebook",notebook);
                            returnIntent.putExtra("index",index);
                            setResult(Activity.RESULT_OK,returnIntent);
                        }

                    }


                }
                if(notebook.getRemind()!=null){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ExactThreadHelper.hel(this,notebook.getRemind(),notebook.getTitle(), notebook.getContent());
                    }else{
                        Toast.makeText(this,"Chưa làm cho bản android này", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.e("NewNote","Finish");

                finish();
                return true;
            case R.id.menuChangeColor:
                contentLayout.setBackgroundResource(R.drawable.ic_bgblue1);
                return true;
            case R.id.reminder:
                // Get Current Date
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                final Calendar c = Calendar.getInstance();
//
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(NewNoteActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                                c.set(Calendar.MINUTE,minute);



                                                notebook.setRemind(new Date(c.getTimeInMillis()));
                                                Log.e("setRemind", Tool.DateToString(new Date(c.getTimeInMillis())));

                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_note_menu,menu);
        return true;
    }
    private void OpenMenuFab(){
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(overshootInterpolator).rotation(45f).setDuration(300).start();
        fabFile.animate().translationY(0f).alpha(1f).setInterpolator(overshootInterpolator).setDuration(300);
        fabCamera.animate().translationY(0f).alpha(1f).setInterpolator(overshootInterpolator).setDuration(300);
    }
    private void CloseMenuFab(){
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(overshootInterpolator).rotation(0f).setDuration(300).start();
        fabFile.animate().translationY(translationY).alpha(0f).setInterpolator(overshootInterpolator).setDuration(300);
        fabCamera.animate().translationY(translationY).alpha(0f).setInterpolator(overshootInterpolator).setDuration(300);
    }
    private void FabOnClick() {
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen){
                    CloseMenuFab();
                }else {
                    OpenMenuFab();
                }
            }
        });
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                CloseMenuFab();
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    Log.e("os version", ">= M");
                    PermissionsCamera();
                } else{
                    Log.e("os version", "< M");
                    dispatchTakePictureIntent();
                }

            }
        });
        fabFile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                CloseMenuFab();
                PermissionsGallery();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void PermissionsCamera(){

            if (checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
            ){
                dispatchTakePictureIntent();
            }else {
                String [] Premissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this,Premissions, PERM_CODE);
            }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void PermissionsGallery(){

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
        ){
            OpenGallery();
        }else {
            String [] Premissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,Premissions, PERM_CODE_GALLERY);
        }
    }

    private void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(this, "denied authorization", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == PERM_CODE_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                OpenGallery();
            }else {
                Toast.makeText(this, "denied authorization", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            setPic();
            newNoteAdapter.notifyDataSetChanged();
        }else if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                notebook.getImages().add(Tool.getByteFromBitmap(bitmap));
                bitmap.recycle();

                newNoteAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("takePicture", ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.mobile.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
            }
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = 1920;
        int targetH = 1080;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, targetW, targetH,
                matrix, true);
        notebook.getImages().add(Tool.getByteFromBitmap(bitmap1));
        bitmap1.recycle();
    }
}