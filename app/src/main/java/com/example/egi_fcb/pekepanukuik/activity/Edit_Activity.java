package com.example.egi_fcb.pekepanukuik.activity;

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

import com.example.egi_fcb.pekepanukuik.sqlite.DBDataSource;
import com.example.egi_fcb.pekepanukuik.sqlite.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.R;

import java.util.Calendar;

public class Edit_Activity extends AppCompatActivity {
    private DBDataSource dbDataSource;

    private long id;
    private String hari,tanggal,jam,pekerjaan, jamakhir;

    private EditText edhari;
    private static EditText edtanggal;
    private static EditText edjam;
    private EditText edpekerjaan;
    private static EditText edjamakhir;

    private Button edit;

    private PengingatPekerjaan pengingatPekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan_material);

        //Ganti Nama ActionBar
        getSupportActionBar().setTitle("Edit Pekerjaan");

        //Tombol Kembali
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edhari = (EditText)findViewById(R.id.hari);
        edtanggal = (EditText)findViewById(R.id.tanggal);
        edjam = (EditText)findViewById(R.id.jam);
        edjamakhir = (EditText)findViewById(R.id.jam_akhir);
        edpekerjaan = (EditText)findViewById(R.id.pekerjaan);
        edit = (Button)findViewById(R.id.simpan);

        //Sambungan Database
        dbDataSource = new DBDataSource(this);
        dbDataSource.open();

        //ambil data barang
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getLong("_id");
        hari = bundle.getString("hari");
        tanggal = bundle.getString("tanggal");
        jam = bundle.getString("jam");
        jamakhir = bundle.getString("jam_akhir");
        pekerjaan = bundle.getString("pekerjaan");

        //masukan data
        edhari.setText(hari);
        edtanggal.setText(tanggal);
        edjam.setText(jam);
        edjamakhir.setText(jamakhir);
        edpekerjaan.setText(pekerjaan);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pengingatPekerjaan = new PengingatPekerjaan();
                pengingatPekerjaan.setTxt_hari(edhari.getText().toString());
                pengingatPekerjaan.setTxt_taggal(edtanggal.getText().toString());
                pengingatPekerjaan.setTxt_jam(edjam.getText().toString());
                pengingatPekerjaan.setTxt_jam_akhir(edjamakhir.getText().toString());
                pengingatPekerjaan.setTxt_pekerjaan(edpekerjaan.getText().toString());
                pengingatPekerjaan.setId(id);
                dbDataSource.updatepekerjaan(pengingatPekerjaan);
                Intent intent = new Intent(getApplicationContext(), Activity_Utama.class);
                startActivity(intent);
                Edit_Activity.this.finishActivity(0);
                dbDataSource.close();
                Toast.makeText(getBaseContext(), "DATA SUDAH BERUBAH", Toast.LENGTH_LONG).show();
            }
        });

        edtanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    DialogFragment dialogFragment = new DatePicker();
                    dialogFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return false;
            }
        });

        edjam.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    DialogFragment dialogFragment = new TimePicker();
                    dialogFragment.show(getSupportFragmentManager(), "timePicker");
                }
                return false;
            }
        });

        edjamakhir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    DialogFragment dialogFragment = new TimePickerr();
                    dialogFragment.show(getSupportFragmentManager(), "timePicker");
                }
                return false;
            }
        });
    }

    public static class TimePickerr extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        public Dialog onCreateDialog(Bundle savedInstanceState){

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
            edjamakhir.setText(hasil);
        }
    }

    public static  class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        public Dialog onCreateDialog(Bundle savedInstanceState){

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

    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Activity_Utama.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}
