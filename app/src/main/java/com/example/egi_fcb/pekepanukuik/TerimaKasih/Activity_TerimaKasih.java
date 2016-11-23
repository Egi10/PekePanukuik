package com.example.egi_fcb.pekepanukuik.TerimaKasih;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.DaftarListView.Daftar_Costum_ListView;
import com.example.egi_fcb.pekepanukuik.Database.DBHelper;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.TampilanAwalGridView.MainActivity;

import java.util.ArrayList;

public class Activity_TerimaKasih extends AppCompatActivity {

    ListView list;
    String[] Namaucapan = {"Ahmad Darma Sani","Teman-Teman Yang Membantu","Hagoromo Otsutsuki"};
    String[] UCapan = { "Saya ucapkan banyak terima kasih atas segala kontribusinya dalam pembuatan Logo Aplikasi ini. " +
                        "Semoga Tuhan yang membalas segala kebaikan yang saudara berikan kepada saya.",
                        "Saya ucapkan terima kasih kepada semua teman-teman dan abang-abang atas bantuan dalam mengerjakan Aplikasi ini." +
                        "Saya meminta maaf tidak bisa saya sebutkan satu persatu. Semoga Tuhan yang membalas segala kebaikan yang " +
                        "teman-teman dan abang-abang berikan kepada saya", "Saya ucapkan banyak terima kasih atas segala kontribusinya dalam pembuatan Aplikasi ini. " +
                        "Semoga Tuhan yang membalas segala kebaikan yang saudara berikan kepada saya."};
    Integer[] Gbrucapan={R.drawable.ads,R.drawable.makasih, R.drawable.ah};

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
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        Activity_TerimaKasih.this.finish();
        return true;
    }
}