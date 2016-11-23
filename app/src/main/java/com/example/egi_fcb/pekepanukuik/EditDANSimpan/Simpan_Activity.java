package com.example.egi_fcb.pekepanukuik.EditDANSimpan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.egi_fcb.pekepanukuik.CardViewdanRecyclerView.CardViewandRecyclerView;
import com.example.egi_fcb.pekepanukuik.DaftarListView.Daftar_Costum_ListView;
import com.example.egi_fcb.pekepanukuik.Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.Database.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.R;
import com.example.egi_fcb.pekepanukuik.TampilanAwalGridView.MainActivity;

import java.util.Calendar;

public class Simpan_Activity extends AppCompatActivity {

    private Button simpan;
    private EditText edhari, edtanggal, edjam, edpekerjaan;
    private DBDataSource dbDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan);

        simpan = (Button)findViewById(R.id.simpan);
        edhari = (EditText)findViewById(R.id.hari);
        edtanggal = (EditText)findViewById(R.id.tanggal);
        edjam = (EditText)findViewById(R.id.jam);
        edpekerjaan = (EditText)findViewById(R.id.pekerjaan);

        getSupportActionBar().setTitle("Simpan Pekerjaan");

        //Tombol Kembali
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbDataSource = new DBDataSource(this);
        dbDataSource.open();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hari = null;
                String tanggal = null;
                String jam = null;
                String pekerjaan = null;

                hari = edhari.getText().toString();
                tanggal = edtanggal.getText().toString();
                jam = edjam.getText().toString();
                pekerjaan = edpekerjaan.getText().toString();

                PengingatPekerjaan pengingatPekerjaan = null;

                if(hari.equals("")){
                    if(!tanggal.equals("")){
                        if (!jam.equals("")){
                            if(!pekerjaan.equals("")){

                            }else {
                                Toast.makeText(getApplicationContext(),"PEKERJAAN MASIH KOSONG",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"JAM MASIH KOSONG",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"TANGGAL MASIH KOSONG",Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(),"TEMPAT MASIH KOSONG",Toast.LENGTH_LONG).show();
                }else{
                    pengingatPekerjaan = dbDataSource.createpake(hari, tanggal, jam, pekerjaan);

                    Toast.makeText(getApplicationContext(), ""+pengingatPekerjaan.getTxt_hari()+
                            ","+pengingatPekerjaan.getTxt_taggal()+
                            "\n"+pengingatPekerjaan.getTxt_jam()+
                            "\n"+pengingatPekerjaan.getTxt_pekerjaan(), Toast.LENGTH_LONG).show();
                    edhari.setText("");
                    edtanggal.setText("");
                    edjam.setText("");
                    edpekerjaan.setText("");

                    Intent intent = new Intent(getApplicationContext(), Daftar_Costum_ListView.class);
                    startActivity(intent);
                    Simpan_Activity.this.finish();
                }
            }
        });

        edjam.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    DialogFragment dialogFragment = new TimePicker();
                    dialogFragment.show(getSupportFragmentManager(), "timePicker");
                }
                return true;
            }
        });

        edtanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DialogFragment dialogFragment = new DatePicker();
                    dialogFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });
    }
    public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        EditText edjam;
        public Dialog onCreateDialog(Bundle savedInstanceState){

            edjam = (EditText)findViewById(R.id.jam);

            final Calendar calendar = Calendar.getInstance();
            int jam = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, jam, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            String hour = String.valueOf(hourOfDay);
            String minu = String.valueOf(minute);

            if(hourOfDay < 10){
                hour = "0" + hourOfDay;
            }

            if(minute < 10){
                minu = "0" + minute;
            }

            String hasil = hour +":"+ minu +" WIB";
            edjam.setText(hasil);
        }
    }

    public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        EditText edtanggal;
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();

            edtanggal = (EditText)findViewById(R.id.tanggal);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int month = monthOfYear + 1;

            String nama_bulan = String.valueOf(month);

            if(month == 1){
                nama_bulan = "Januari";
            }else if(month == 2){
                nama_bulan = "February";
            }else if(month == 3){
                nama_bulan = "Maret";
            }else if(month == 4){
                nama_bulan = "April";
            }else if(month == 5){
                nama_bulan = "Mei";
            }else if(month == 6){
                nama_bulan = "Juni";
            }else if(month == 7){
                nama_bulan = "Juli";
            }else if(month == 8){
                nama_bulan = "Agustus";
            }else if(month == 9){
                nama_bulan = "September";
            }else if(month == 10){
                nama_bulan = "Oktober";
            }else if(month == 11){
                nama_bulan = "November";
            }else if(month == 12){
                nama_bulan = "Desember";
            }

            String day = String.valueOf(dayOfMonth);
            if(dayOfMonth < 10){
                day = "0" + dayOfMonth;
            }

            edtanggal.setText(day+" "+nama_bulan+" "+year);
        }
    }

    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        Simpan_Activity.this.finish();
        return true;
    }
}