package com.example.egi_fcb.pekepanukuik.TampilanAwalGridView;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.egi_fcb.pekepanukuik.CardViewdanRecyclerView.CardViewandRecyclerView;
import com.example.egi_fcb.pekepanukuik.DaftarListView.Daftar_Costum_ListView;
import com.example.egi_fcb.pekepanukuik.EditDANSimpan.Simpan_Activity;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.Tentang.Tentang_Activity;
import com.example.egi_fcb.pekepanukuik.TerimaKasih.Activity_TerimaKasih;
import com.example.egi_fcb.pekepanukuik.ImageSlider_CaraPenggunaan.ActivityImageSlider;

public class MainActivity extends AppCompatActivity {

    CostumAdapter costumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__grid__view);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        costumAdapter = new CostumAdapter(this);
        gridView.setAdapter(costumAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0 :
                        Intent intent = new Intent(getApplicationContext(), Simpan_Activity.class);
                        intent.putExtra("posisi", i);
                        startActivity(intent);
                        MainActivity.this.finish();
                        break;
                    case 1 :
                        Intent intent1 = new Intent(getApplicationContext(), Daftar_Costum_ListView.class);
                        intent1.putExtra("posisi", i);
                        startActivity(intent1);
                        MainActivity.this.finishActivity(0);
                        break;
                    case 2 :
                        Intent intent2 = new Intent(getApplicationContext(), CardViewandRecyclerView.class);
                        intent2.putExtra("posisi", i);
                        startActivity(intent2);
                        MainActivity.this.finishActivity(0);
                        break;
                    case 3 :
                        AlertDialog.Builder alBuilder = new AlertDialog.Builder(MainActivity.this);
                        alBuilder.setMessage("Apakah Anda Ingin Keluar ?");
                        alBuilder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent keluar = new Intent(Intent.ACTION_MAIN);
                                keluar.addCategory(Intent.CATEGORY_HOME);
                                keluar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(keluar);
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                        break;
                }
            }
        });
    }
    //MenuItem
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pilihan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.cara_penggunaan:
                Intent intent2 = new Intent(getApplicationContext(), ActivityImageSlider.class);
                startActivity(intent2);
                MainActivity.this.finish();
                return true;

            case R.id.tentang:
                Intent intent1 = new Intent(getApplicationContext(), Tentang_Activity.class);
                startActivity(intent1);
                MainActivity.this.finish();
                return true;

            case R.id.terima_kasih:
                Intent intent = new Intent(getApplicationContext(), Activity_TerimaKasih.class);
                startActivity(intent);
                MainActivity.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}