package com.example.egi_fcb.pekepanukuik.ZUsangTampilanAwalGridView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.egi_fcb.pekepanukuik.ZUsangImageSlider_CaraPenggunaan.ActivityImageSlider;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.Package_Tentang.Tentang_Activity;
import com.example.egi_fcb.pekepanukuik.Package_Terima_Kasih.Activity_TerimaKasih;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egi_fcb on 1/26/17.
 */

public class Tampilan_Awal extends AppCompatActivity {
    RecyclerView recyclerView;
    AdView mAdView;
    Context context;

    RecyclerAdapter recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Pengingat Pekerjaan");
        setContentView(R.layout.layout_tampilan_awal);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("13B6C2E15F833A46B77A11DBA1CE98C5").build();
        mAdView.loadAd(adRequest);

        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView_Adapter = new RecyclerAdapter(context,getUserInformation());
        recyclerView.setAdapter(recyclerView_Adapter);
    }

    private List<User> getUserInformation() {

        List<User> userList = new ArrayList<User>();
        userList.add(new User(R.mipmap.image_input,"MASUKAN"));
        userList.add(new User(R.mipmap.image_list,"DAFTAR"));
        userList.add(new User(R.mipmap.image_sampah,"SAMPAH"));
        userList.add(new User(R.mipmap.image_keluar,"KELUAR"));
        return userList;
    }

    public class User {
        private int imageResourceId;
        private String profileName;

        public int getImageResourceId() {
            return imageResourceId;
        }
        public String getProfileName() {
            return profileName;
        }

        public User(int imageResourceId, String profileName) {
            this.imageResourceId = imageResourceId;
            this.profileName = profileName;
        }
    }

    //MenuItem
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pilihan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // return to the App's Home Activity
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alBuilder = new AlertDialog.Builder(Tampilan_Awal.this);
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
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.cara_penggunaan:
                Intent intent2 = new Intent(getApplicationContext(), ActivityImageSlider.class);
                startActivity(intent2);
                Tampilan_Awal.this.finish();
                return true;

            case R.id.tentang:
                Intent intent1 = new Intent(getApplicationContext(), Tentang_Activity.class);
                startActivity(intent1);
                Tampilan_Awal.this.finish();
                return true;

            case R.id.terima_kasih:
                Intent intent = new Intent(getApplicationContext(), Activity_TerimaKasih.class);
                startActivity(intent);
                Tampilan_Awal.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
