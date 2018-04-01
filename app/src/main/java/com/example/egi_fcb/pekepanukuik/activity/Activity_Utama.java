package com.example.egi_fcb.pekepanukuik.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egi_fcb.pekepanukuik.adapter.ListAdapter;
import com.example.egi_fcb.pekepanukuik.sqlite.DBDataSource;
import com.example.egi_fcb.pekepanukuik.sqlite.DBHelper;
import com.example.egi_fcb.pekepanukuik.sqlite.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Activity_Utama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView daftar;
    private DBDataSource dbDataSource;
    TextView getid;
    private PengingatPekerjaan pengingatPekerjaan;
    Cursor cursor;
    RelativeLayout relativeLayout;
    final Context context = this;
    ListAdapter adapter;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Utama.this, Simpan_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Ganti Nama ActionBar
        getSupportActionBar().setTitle("Daftar Pekerjaan");


        //Tombol Kembali
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        relativeLayout = (RelativeLayout)findViewById(R.id.emptyView);
        daftar = (ListView) findViewById(R.id.listView);

        //Pemanggilan Data
        dbDataSource = new DBDataSource(this);
        dbDataSource.open();

        //Menampilkan Data
        final Cursor cursor = dbDataSource.getall();

        //Menampilkan Data Pekerjaan Belum Ada
        if (cursor.getCount() < 1) {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        daftar.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new ListAdapter(this, cursor,0);
        daftar.setAdapter(adapter);

        daftar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int position, long l) {
                TextView tx = (TextView)view.findViewById(R.id.lvid);
                final long id = Long.parseLong(tx.getText().toString());
                final CharSequence[] pilihan = {"Lihat Pekerjaan","Edit Pekerjaan","Hapus Pekerjaan"};
                if(daftar.getItemAtPosition(position).toString() != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Utama.this);
                    builder.setTitle("Pilihan");
                    builder.setItems(pilihan, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            if(pilihan[which] == "Lihat Pekerjaan"){
                                getid = (TextView) view.findViewById(R.id.lvid);
                                final long id = Long.parseLong(getid.getText().toString());
                                Cursor cursor1 = dbDataSource.satuData(id);
                                cursor1.moveToFirst();

                                String hari = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_hari));
                                String jam = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam));
                                String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                                String jam_akhir = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam_akhir));
                                String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Utama.this);
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
                                txjam_akhir.setText(jam_akhir);
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
                            }
                            else if(pilihan[which] == "Edit Pekerjaan"){
                                editdata(id);
                                dialog.dismiss();
                            }
                            else if(pilihan[which] == "Hapus Pekerjaan"){
                                final AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
                                final View view1 = getLayoutInflater().inflate(R.layout.costumview_alertdialog, null);
                                alertDialog1.setView(view1);
                                alertDialog1.setTitle("Hapus Pekerjaan");

                                final AlertDialog dialog1 = alertDialog1.create();
                                dialog1.show();

                                TextView textView = (TextView)view1.findViewById(R.id.cadtext);
                                Button button = (Button)view1.findViewById(R.id.btniya);
                                Button button1 = (Button)view1.findViewById(R.id.btntidak);
                                final CheckBox checkBox = (CheckBox)view1.findViewById(R.id.checkBox);

                                textView.setText("Apakah Anda Yakin Menghapus Pekerjaan ?");

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(checkBox.isChecked()){
                                            dbDataSource.deletepekerjaan(id);
                                            dialog1.dismiss();
                                            Cursor c = dbDataSource.getall();
                                            adapter.swapCursor(c);
                                            daftar.setAdapter(adapter);
                                            Toast.makeText(getBaseContext(), "DATA SUDAH TERHAPUS", Toast.LENGTH_LONG).show();

                                            //Menampilkan Data
                                            final Cursor cursor = dbDataSource.getall();

                                            //Menampilkan Data Pekerjaan Belum Ada
                                            if (cursor.getCount() < 1) {
                                                relativeLayout.setVisibility(View.VISIBLE);
                                            }
                                        }else {
                                            Cursor cursor1 = dbDataSource.satuData(id);
                                            cursor1.moveToFirst();

                                            String hari = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_hari));
                                            String jam = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam));
                                            String jam_akhir = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_jam_akhir));
                                            String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                                            String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                                            pengingatPekerjaan = dbDataSource.createsampah(hari, tanggal, jam, jam_akhir, pekerjaan);

                                            dbDataSource.deletepekerjaan(id);
                                            dialog1.dismiss();
                                            Cursor c = dbDataSource.getall();
                                            adapter.swapCursor(c);
                                            daftar.setAdapter(adapter);
                                            Toast.makeText(getBaseContext(), "DATA SUDAH TERHAPUS", Toast.LENGTH_LONG).show();

                                            //Menampilkan Data
                                            final Cursor cursor = dbDataSource.getall();

                                            //Menampilkan Data Pekerjaan Belum Ada
                                            if (cursor.getCount() < 1) {
                                                relativeLayout.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        dialog1.dismiss();
                                    }
                                });
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                    }
                                });
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return false;
            }
            //menthod untuk edit data
            public void editdata(long id){
                PengingatPekerjaan pengingatPekerjaan = dbDataSource.getpekerjaan(id);
                Intent intent = new Intent(getApplicationContext(), Edit_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("_id", pengingatPekerjaan.getId());
                bundle.putString("hari", pengingatPekerjaan.getTxt_hari());
                bundle.putString("tanggal", pengingatPekerjaan.getTxt_taggal());
                bundle.putString("jam", pengingatPekerjaan.getTxt_jam());
                bundle.putString("jam_akhir", pengingatPekerjaan.getTxt_jam_akhir());
                bundle.putString("pekerjaan", pengingatPekerjaan.getTxt_pekerjaan());
                intent.putExtras(bundle);
                finale();
                startActivity(intent);
                finish();
            }

            public void finale(){
                dbDataSource.close();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity__utama, menu);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            SearchManager manager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
            mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
            mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    cursor = dbDataSource.getPekerjaanListByKeyword(query);
                    adapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    cursor = dbDataSource.getPekerjaanListByKeyword(newText);
                    adapter.swapCursor(cursor);
                    if (adapter.getCount() < 1){
                        Toast.makeText(getBaseContext(),"Tidak Ada Data", Toast.LENGTH_LONG).show();
                    }
                    //else {
                    //Toast.makeText(getBaseContext(), adapter.getCount()+"records found!", Toast.LENGTH_LONG).show();
                    //}
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(Activity_Utama.this, Activity_Trash.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Activity_Utama.this, Activity_TerimaKasih.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(Activity_Utama.this, Tentang_Activity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.egi_fcb.pekepanukuik&hl=in"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // return to the App's Home Activity
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alBuilder = new AlertDialog.Builder(Activity_Utama.this);
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
            });
            alBuilder.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
