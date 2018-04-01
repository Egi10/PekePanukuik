package com.example.egi_fcb.pekepanukuik.Package_Terima_Kasih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.egi_fcb.pekepanukuik.Packaga_Tampilan_Utama.Activity_Utama;
import com.example.egi_fcb.pekepanukuik.R;

public class Activity_TerimaKasih extends AppCompatActivity {

    ListView list;
    String[] Namaucapan = {"Ahmad Darma Sani","Teman-Teman Yang Membantu"};
    String[] UCapan = { "Saya ucapkan banyak terima kasih atas segala kontribusinya dalam pembuatan Logo Aplikasi ini. " +
                        "Semoga Tuhan yang membalas segala kebaikan yang saudara berikan kepada saya.",
                        "Saya ucapkan terima kasih kepada semua teman-teman dan abang-abang atas bantuan dalam mengerjakan Aplikasi ini." +
                        "Saya meminta maaf tidak bisa saya sebutkan satu persatu. Semoga Tuhan yang membalas segala kebaikan yang " +
                        "teman-teman dan abang-abang berikan kepada saya"};
    Integer[] Gbrucapan={R.mipmap.image_ads,R.mipmap.image_makasih};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Terima Kasih");
        setContentView(R.layout.activity__terima_kasih);

        Adapter_TerimaKasih adapter=new Adapter_TerimaKasih(this, Namaucapan, UCapan, Gbrucapan);
        list=(ListView)findViewById(R.id.listViewterimakasih);
        list.setAdapter(adapter);
    }

    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Activity_Utama.class);
        startActivityForResult(myIntent, 0);
        Activity_TerimaKasih.this.finish();
        return true;
    }
}