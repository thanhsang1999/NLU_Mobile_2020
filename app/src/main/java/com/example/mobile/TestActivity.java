package com.example.mobile;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;



public class TestActivity extends Activity {

    Button btnLoadImage;
    ImageView imageResult;

    final int RQS_IMAGE1 = 1;

    Uri source;
    Bitmap bitmapMaster;
    Canvas canvasMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnLoadImage = (Button) findViewById(R.id.loadimage);
        imageResult = (ImageView) findViewById(R.id.result);

        btnLoadImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    source = data.getData();

                    try {
                        bitmapMaster = BitmapFactory
                                .decodeStream(getContentResolver().openInputStream(
                                        source));
                        loadGrayBitmap(bitmapMaster);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    private void loadGrayBitmap(Bitmap src) {
        if (src != null) {

            int w = src.getWidth();
            int h = src.getHeight();
            RectF rectF = new RectF(w/4, h/4, w*3/4, h*3/4);
            float blurRadius = 100.0f;

            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvasResult = new Canvas(bitmapResult);

            Paint blurPaintOuter = new Paint();
            blurPaintOuter.setColor(0xFFffffff);
            blurPaintOuter.setMaskFilter(new
                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.INNER));
            canvasResult.drawBitmap(bitmapMaster, 0, 0, blurPaintOuter);

            Paint blurPaintInner = new Paint();
            blurPaintInner.setColor(0xFFffffff);
            blurPaintInner.setMaskFilter(new
                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
            canvasResult.drawRect(rectF, blurPaintInner);

            imageResult.setImageBitmap(bitmapResult);
        }
    }
}