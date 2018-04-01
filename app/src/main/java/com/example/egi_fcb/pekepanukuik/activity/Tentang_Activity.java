package com.example.egi_fcb.pekepanukuik.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.R;

public class Tentang_Activity extends AppCompatActivity {

    TextView tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tentang");
        setContentView(R.layout.activity_tentang);

        tentang = (TextView)findViewById(R.id.tentang2);

        String tentang1 = "Ini adalah sebuah aplikasi yang dirancang untuk bisa mengingatkan " +
                          "kita dalam melakukan pekerjaan kita.";

        tentang.setText(tentang1);
    }

    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Activity_Utama.class);
        startActivityForResult(myIntent, 0);
        Tentang_Activity.this.finish();
        return true;
    }
}
