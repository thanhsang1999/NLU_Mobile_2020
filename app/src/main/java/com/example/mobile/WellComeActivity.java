package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

public class WellComeActivity extends AppCompatActivity {
    int[] image= new int[]{R.drawable.ic_wellcome_1, R.drawable.ic_wellcome_2,R.drawable.ic_wellcome_3};
//    String [] titles={getString(R.string.wellcome),getString(R.string.wellcome_1),getString(R.string.wellcome_2),getString(R.string.wellcome_3)};
    int [] titles2={R.string.wellcome_1,R.string.wellcome_2,R.string.wellcome_3};
    TextView textViewDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
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
                textViewDescription.setText(titles2[position]);
            }

            @Override
            public void onPageSelected(int position) {
                textViewDescription.setText(titles2[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Button btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btnLogIn = findViewById(R.id.btn_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellComeActivity.this, LogInActivity.class);

                startActivity(intent);

            }
        });

    }
}