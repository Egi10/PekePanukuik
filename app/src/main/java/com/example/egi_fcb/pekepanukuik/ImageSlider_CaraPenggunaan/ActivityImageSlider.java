package com.example.egi_fcb.pekepanukuik.ImageSlider_CaraPenggunaan;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.egi_fcb.pekepanukuik.Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.TampilanAwalGridView.MainActivity;

public class ActivityImageSlider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cara Penggunaan");
        setContentView(R.layout.activity_image_slider);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        SliderImageAdapter adapterView = new SliderImageAdapter(this);
        mViewPager.setAdapter(adapterView);
    }

    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
