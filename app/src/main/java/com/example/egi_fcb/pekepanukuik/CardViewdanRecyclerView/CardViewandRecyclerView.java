package com.example.egi_fcb.pekepanukuik.CardViewdanRecyclerView;

import android.app.Activity;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egi_fcb.pekepanukuik.DaftarListView.Adapter;
import com.example.egi_fcb.pekepanukuik.DaftarListView.Daftar_Costum_ListView;
import com.example.egi_fcb.pekepanukuik.Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.Database.DBHelper;
import com.example.egi_fcb.pekepanukuik.Database.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.TampilanAwalGridView.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class CardViewandRecyclerView extends AppCompatActivity implements ClickListener{

    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    private DBDataSource dbDataSource;
    RelativeLayout relativeLayout;
    SearchView mSearchView;
    final Context context = this;
    Adapter adapter;
    private final static String TAG= CardViewandRecyclerView.class.getName().toString();
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
                    ArrayList<PengingatPekerjaan> coba = dbDataSource.getPekerjaanListBySampah(query);
                    recyclerAdapter = new RecyclerAdapter(coba);
                    recyclerView.setAdapter(recyclerAdapter);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(TAG, "onQueryTextChange");
                    ArrayList<PengingatPekerjaan> tes = dbDataSource.getPekerjaanListBySampah(newText);
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
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();

        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.edit_pekerjaan:
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
    public void itemClicked(final View v, int adapterPosition) {
        //Pemanggilan Data
        dbDataSource = new DBDataSource(this);
        dbDataSource.open();
        final CharSequence[] pilihan = {"Lihat Sampah","Hapus Sampah","Kembalikan Ke Pekerjaan"};

        recyclerAdapter.getItemCount();

        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(adapterPosition);

        if(viewHolder != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CardViewandRecyclerView.this);
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
                        String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                        String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                        final AlertDialog.Builder builder = new AlertDialog.Builder(CardViewandRecyclerView.this);
                        builder.setTitle("Detail Pekerjaan");
                        builder.setMessage("Tempat        : " + hari + "\n" +
                                "Tanggal       : " + tanggal + "\n" +
                                "Jam              : " + jam + "\n" +
                                "Pekerjaan    : " + pekerjaan);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    } else if (pilihan[i] == "Hapus Sampah") {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CardViewandRecyclerView.this);
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
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();




                    } else if (pilihan[i] == "Kembalikan Ke Pekerjaan") {
                        TextView getid = (TextView) v.findViewById(R.id.lvid2);
                        final long id = Long.parseLong(String.valueOf(getid.getText()).toString());
                        Cursor cursor1 = dbDataSource.satuDatasampah(id);
                        cursor1.moveToFirst();

                        String hari = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_hari));
                        String jam = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam));
                        String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                        String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                        pengingatPekerjaan = dbDataSource.createpake(hari, tanggal, jam, pekerjaan);

                        dbDataSource.deletesampah(id);
                        dialogInterface.dismiss();

                        List<PengingatPekerjaan> users = dbDataSource.getAllSampah();
                        recyclerAdapter = new RecyclerAdapter(users);
                        recyclerView.setAdapter(recyclerAdapter);
                        Toast.makeText(getBaseContext(), "Pekerjaan Dikembalikan", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
}