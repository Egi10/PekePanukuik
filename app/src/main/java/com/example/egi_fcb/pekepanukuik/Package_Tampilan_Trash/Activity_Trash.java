package com.example.egi_fcb.pekepanukuik.Package_Tampilan_Trash;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egi_fcb.pekepanukuik.Packaga_Tampilan_Utama.ListAdapter;
import com.example.egi_fcb.pekepanukuik.Package_Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.Package_Database.DBHelper;
import com.example.egi_fcb.pekepanukuik.Package_Database.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.Packaga_Tampilan_Utama.Activity_Utama;
import com.example.egi_fcb.pekepanukuik.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;


public class Activity_Trash extends AppCompatActivity implements ClickListener{

    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    private DBDataSource dbDataSource;
    RelativeLayout relativeLayout;
    SearchView mSearchView;
    final Context context = this;
    ListAdapter adapter;
    private final static String TAG= Activity_Trash.class.getName().toString();
    Cursor cursor;
    private List<PengingatPekerjaan> mdataset;
    private PengingatPekerjaan pengingatPekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewand_recycler_view);

        //Ganti Nama ActionBar
        getSupportActionBar().setTitle("Daftar Sampah");

        //Tombol Kembali
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        relativeLayout = (RelativeLayout)findViewById(R.id.emptyView);

        dbDataSource = new DBDataSource(this);
        dbDataSource.open();
        List<PengingatPekerjaan> users = dbDataSource.getAllSampah();
        if (users.size()==0){
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            recyclerAdapter = new RecyclerAdapter(users);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerAdapter.notifyDataSetChanged();
            recyclerAdapter.setClickListener(this);
        }
    }

    //MenuItem
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            SearchManager manager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
            mSearchView = (SearchView) menu.findItem(R.id.search2).getActionView();
            mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "onQueryTextSubmit");
                    List<PengingatPekerjaan> coba = dbDataSource.getPekerjaanListBySampah(query);
                    recyclerAdapter = new RecyclerAdapter(coba);
                    recyclerView.setAdapter(recyclerAdapter);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(TAG, "onQueryTextChange");
                    List<PengingatPekerjaan> tes = dbDataSource.getPekerjaanListBySampah(newText);
                    recyclerAdapter = new RecyclerAdapter(tes);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setHasFixedSize(true);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Activity_Utama.class);
        startActivityForResult(myIntent, 0);
        finish();

        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.bersihkan:
                dbDataSource = new DBDataSource(this);
                dbDataSource.open();
                dbDataSource.deletesampahsemua();
                // search action
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // return to the App's Home Activity
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent myIntent = new Intent(getApplicationContext(), Activity_Utama.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void itemClicked(final View v, int adapterPosition) {
        //Pemanggilan Data
        dbDataSource = new DBDataSource(this);
        dbDataSource.open();
        final CharSequence[] pilihan = {"Lihat Sampah","Hapus Sampah","Kembalikan Ke Pekerjaan"};

        recyclerAdapter.getItemCount();

        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(adapterPosition);

        if(viewHolder != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Trash.this);
            builder.setTitle("Pilihan");
            builder.setItems(pilihan, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (pilihan[i] == "Lihat Sampah") {
                        TextView getid = (TextView) v.findViewById(R.id.lvid2);
                        final long id = Long.parseLong(String.valueOf(getid.getText()).toString());
                        Cursor cursor1 = dbDataSource.satuDatasampah(id);
                        cursor1.moveToFirst();

                        String hari = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_hari));
                        String jam = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam));
                        String jamakhir = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam_akhir));
                        String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                        String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Trash.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialog_layout = inflater.inflate(R.layout.activity_lihat_material,null);

                        final TextView txhari = (TextView) dialog_layout.findViewById(R.id.hari);
                        final TextView txtanggal = (TextView) dialog_layout.findViewById(R.id.tanggal);
                        final TextView txjam = (TextView) dialog_layout.findViewById(R.id.jam);
                        final TextView txjam_akhir = (TextView) dialog_layout.findViewById(R.id.jam_akhir);
                        final TextView txpekerjaan = (TextView) dialog_layout.findViewById(R.id.pekerjaan);

                        txhari.setText(hari);
                        txtanggal.setText(tanggal);
                        txjam.setText(jam);
                        txjam_akhir.setText(jamakhir);
                        txpekerjaan.setText(pekerjaan);
                        builder.setView(dialog_layout);

                        builder.setTitle("Detail Pekerjaan");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog dialogg = builder.create();
                        dialogg.show();

                    } else if (pilihan[i] == "Hapus Sampah") {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Trash.this);
                        builder1.setTitle("Hapus Sampah");
                        builder1.setMessage("Apakah Anda Yakin Menghapus Sampah ?");
                        builder1.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView getid = (TextView) v.findViewById(R.id.lvid2);
                                final long id = Long.parseLong(String.valueOf(getid.getText()).toString());
                                dbDataSource.deletesampah(id);
                                List<PengingatPekerjaan> users = dbDataSource.getAllSampah();
                                recyclerAdapter = new RecyclerAdapter(users);
                                recyclerView.setAdapter(recyclerAdapter);

                                Toast.makeText(getBaseContext(), "DATA SUDAH TERHAPUS", Toast.LENGTH_LONG).show();

                                //Menampilkan Data
                                List<PengingatPekerjaan> userss = dbDataSource.getAllSampah();

                                //Menampilkan Data Pekerjaan Belum Ada
                                if (userss.size()==0){
                                    relativeLayout.setVisibility(View.VISIBLE);
                                }
                                dialogInterface.dismiss();

                                finish();
                                startActivity(getIntent());
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();

                    } else if (pilihan[i] == "Kembalikan Ke Pekerjaan") {
                        TextView getid = (TextView) v.findViewById(R.id.lvid2);
                        final long id = Long.parseLong(String.valueOf(getid.getText()).toString());
                        Cursor cursor1 = dbDataSource.satuDatasampah(id);
                        cursor1.moveToFirst();

                        String hari = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_hari));
                        String jam = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam));
                        String jam_akhir = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam_akhir));
                        String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                        String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                        pengingatPekerjaan = dbDataSource.createpake(hari, tanggal, jam, jam_akhir, pekerjaan);

                        dbDataSource.deletesampah(id);
                        dialogInterface.dismiss();

                        List<PengingatPekerjaan> users = dbDataSource.getAllSampah();
                        recyclerAdapter = new RecyclerAdapter(users);
                        recyclerView.setAdapter(recyclerAdapter);
                        Toast.makeText(getBaseContext(), "Pekerjaan Dikembalikan", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();

                        finish();
                        startActivity(getIntent());
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}