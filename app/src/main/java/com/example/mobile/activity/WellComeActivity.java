package com.example.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class WellComeActivity extends AppCompatActivity {
    int[] image= new int[]{R.drawable.ic_wellcome_1, R.drawable.ic_wellcome_2,R.drawable.ic_wellcome_3};
//    String [] titles={getString(R.string.wellcome),getString(R.string.wellcome_1),getString(R.string.wellcome_2),getString(R.string.wellcome_3)};
    int [] titleImages ={R.string.wellcome_1,R.string.wellcome_2,R.string.wellcome_3};
    TextView textViewDescription;
    Button buttonSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        init();
        CarouselView carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(image.length);
        textViewDescription= findViewById(R.id.textViewDescription);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(image[position]);


            }
        });
        carouselView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                textViewDescription.setText(titleImages[position]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellComeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btnLogIn = findViewById(R.id.btn_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellComeActivity.this, LogInActivity.class);

                startActivity(intent);
                finish();

            }
        });

    }

    private void init() {
        buttonSkip = findViewById(R.id.btn_skip);
    }
}