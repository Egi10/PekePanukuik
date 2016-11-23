package com.example.egi_fcb.pekepanukuik.DaftarListView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egi_fcb.pekepanukuik.Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.Database.DBHelper;
import com.example.egi_fcb.pekepanukuik.Database.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.EditDANSimpan.Edit_Activity;
import com.example.egi_fcb.pekepanukuik.EditDANSimpan.Simpan_Activity;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.TampilanAwalGridView.MainActivity;

public class Daftar_Costum_ListView extends AppCompatActivity {

    private ListView daftar;
    private DBDataSource dbDataSource;
    TextView getid;
    private PengingatPekerjaan pengingatPekerjaan;
    Cursor cursor;
    RelativeLayout relativeLayout;
    final Context context = this;
    Adapter adapter;
    SearchView mSearchView;
    private final static String TAG= Daftar_Costum_ListView.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_costum_list_view);

        //Ganti Nama ActionBar
        getSupportActionBar().setTitle("Daftar Pekerjaan");


        //Tombol Kembali
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        relativeLayout = (RelativeLayout)findViewById(R.id.emptyView);
        daftar = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Simpan_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //Pemanggilan Data
        dbDataSource = new DBDataSource(this);
        dbDataSource.open();

        //Menampilkan Data
        final Cursor cursor = dbDataSource.getall();

        //Menampilkan Data Pekerjaan Belum Ada
        if (cursor.getCount() < 1) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
        adapter = new Adapter(this, cursor,0);
        daftar.setAdapter(adapter);

        daftar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int position, long l) {
                TextView tx = (TextView)view.findViewById(R.id.lvid);
                final long id = Long.parseLong(tx.getText().toString());
                final CharSequence[] pilihan = {"Lihat Pekerjaan","Edit Pekerjaan","Hapus Pekerjaan"};
                if(daftar.getItemAtPosition(position).toString() != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Daftar_Costum_ListView.this);
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
                                String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                                final AlertDialog.Builder builder = new AlertDialog.Builder(Daftar_Costum_ListView.this);
                                builder.setTitle("Detail Pekerjaan");
                                builder.setMessage("Tempat        : "+hari+"\n"+
                                                   "Tanggal       : "+tanggal+"\n"+
                                                   "Jam              : "+jam+"\n"+
                                                   "Pekerjaan    : "+pekerjaan);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
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
                                            String tanggal = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_tanggal));
                                            String pekerjaan = cursor1.getString(cursor1.getColumnIndex(DBHelper.COLUMN_pekerjaan));

                                            pengingatPekerjaan = dbDataSource.createsampah(hari, tanggal, jam, pekerjaan);

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

    //MenuItem
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cari, menu);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            SearchManager manager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
            mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
            mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "onQueryTextSubmit");
                    cursor = dbDataSource.getPekerjaanListByKeyword(query);
                    adapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(TAG, "onQueryTextChange");
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


    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}
