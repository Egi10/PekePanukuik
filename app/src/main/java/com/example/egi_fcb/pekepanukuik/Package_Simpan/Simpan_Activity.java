package com.example.egi_fcb.pekepanukuik.Package_Simpan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.egi_fcb.pekepanukuik.Package_Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.Package_Database.DBHelper;
import com.example.egi_fcb.pekepanukuik.Package_Database.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.Packaga_Tampilan_Utama.Activity_Utama;
import com.example.egi_fcb.pekepanukuik.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Simpan_Activity extends AppCompatActivity {

    private Button simpan;
    private EditText edhari;
    private static EditText edtanggal;
    private static EditText edjam;
    private static EditText edjamakhir;
    private TextInputLayout inputLayouthari, inputLayouttanggal, inputLayoutjam, inputLayoutjamakhir, inputLayoutpekerjaan;
    private static EditText edpekerjaan;
    private DBDataSource dbDataSource;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan_material);

        inputLayouthari = (TextInputLayout) findViewById(R.id.input_layout_hari);
        inputLayouttanggal = (TextInputLayout) findViewById(R.id.input_layout_tanggal);
        inputLayoutjam = (TextInputLayout) findViewById(R.id.input_layout_jam);
        inputLayoutjamakhir = (TextInputLayout) findViewById(R.id.input_layout_jam_akhir);
        inputLayoutpekerjaan = (TextInputLayout) findViewById(R.id.input_layout_pekerjaan);
        simpan = (Button) findViewById(R.id.simpan);
        edhari = (EditText) findViewById(R.id.hari);
        edtanggal = (EditText) findViewById(R.id.tanggal);
        edjam = (EditText) findViewById(R.id.jam);
        edjamakhir = (EditText) findViewById(R.id.jam_akhir);
        edpekerjaan = (EditText) findViewById(R.id.pekerjaan);

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
                String jam_akhir = null;
                String pekerjaan = null;

                hari = edhari.getText().toString();
                tanggal = edtanggal.getText().toString();
                jam = edjam.getText().toString();
                jam_akhir = edjamakhir.getText().toString();
                pekerjaan = edpekerjaan.getText().toString();

                PengingatPekerjaan pengingatPekerjaan = null;

                if (!validateTempat()) {
                    return;
                } else if (!validateTanggal()) {
                    return;
                } else if (!validateJam()) {
                    return;
                } else if (!validateJamakhir()) {
                    return;
                }else if (!validatePekerjaan()) {
                    return;
                }else {
                    pengingatPekerjaan = dbDataSource.createpake(hari, tanggal, jam, jam_akhir, pekerjaan);

                    Toast.makeText(getApplicationContext(), "" + pengingatPekerjaan.getTxt_hari() +
                            "," + pengingatPekerjaan.getTxt_taggal() +
                            "\n" + pengingatPekerjaan.getTxt_jam() +
                            "\n" + pengingatPekerjaan.getTxt_pekerjaan(), Toast.LENGTH_LONG).show();

                    edhari.setText("");
                    edtanggal.setText("");
                    edjam.setText("");
                    edjamakhir.setText("");
                    edpekerjaan.setText("");

                    Intent intent = new Intent(getApplicationContext(), Activity_Utama.class);
                    startActivity(intent);
                    Simpan_Activity.this.finish();
                }
            }
        });

        edjam.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DialogFragment dialogFragment = new TimePicker();
                    dialogFragment.show(getSupportFragmentManager(), "timePicker");
                }
                return true;
            }
        });

        edjamakhir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    DialogFragment dialogFragment = new TimePicker2();
                    dialogFragment.show(getSupportFragmentManager(), "timePicker");
                }
                return false;
            }
        });
        edtanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DialogFragment dialogFragment = new DatePicker();
                    dialogFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });
    }

    public static class TimePicker2 extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int jam = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, jam, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            String hour = String.valueOf(hourOfDay);
            String minu = String.valueOf(minute);

            if (hourOfDay < 10) {
                hour = "0" + hourOfDay;
            }

            if (minute < 10) {
                minu = "0" + minute;
            }

            String hasil = hour + ":" + minu + " WIB";
            edjamakhir.setText(hasil);
        }
    }

    public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int jam = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, jam, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            String hour = String.valueOf(hourOfDay);
            String minu = String.valueOf(minute);

            if (hourOfDay < 10) {
                hour = "0" + hourOfDay;
            }

            if (minute < 10) {
                minu = "0" + minute;
            }

            //kenapa kode insert disini?
            //itu jelas2 beda, kmu malah set databasenya saat date picker -_-
            //saya gak pakai datapicker langsung dari
            //maksudku ini TimePicker, kmu malah langsung insert ketika TimePicker di set. ?
            //jadi gak bisa ya bg ?
            //mainkan logika kamu. masa langsung insert ketika pilih waktu/tanggal ?
            //kalau saya buat di buutton simpan, nanti pemanggilan datapickernya gak bisa bg, tu harus gettex dari edittext tu bg ?
            //ya kamu impelemntasinya seharusnya di button simpan itu, jangan saat onTimeSet.
            //dan juga kode sqlite mu berantakan, saya jadi pusing
            //saya copy dari projeck yang tadi tu aja bg, say



            String hasil = hour + ":" + minu + " WIB";
            edjam.setText(hasil);
        }
    }

    public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
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

    private boolean validateTempat() {
        String tempat = edhari.getText().toString().trim();

        if (tempat.isEmpty()) {
            inputLayouthari.setError(getString(R.string.err_msg_tempat));
            requestFocus(edhari);
            return false;
        } else {
            inputLayouthari.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTanggal() {
        String tempat = edtanggal.getText().toString().trim();

        if (tempat.isEmpty()) {
            inputLayouttanggal.setError(getString(R.string.err_msg_tanggal));
            requestFocus(edtanggal);
            return false;
        } else {
            inputLayouttanggal.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateJam() {
        String tempat = edjam.getText().toString().trim();

        if (tempat.isEmpty()) {
            inputLayoutjam.setError(getString(R.string.err_msg_jam));
            requestFocus(edjam);
            return false;
        } else {
            inputLayoutjam.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateJamakhir() {
        String tempat = edjamakhir.getText().toString().trim();

        if (tempat.isEmpty()) {
            inputLayoutjamakhir.setError(getString(R.string.err_msg_jamakhir));
            requestFocus(edjam);
            return false;
        } else {
            inputLayoutjamakhir.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePekerjaan() {
        String tempat = edpekerjaan.getText().toString().trim();

        if (tempat.isEmpty()) {
            inputLayoutpekerjaan.setError(getString(R.string.err_msg_pekerjaan));
            requestFocus(edpekerjaan);
            return false;
        } else {
            inputLayoutpekerjaan.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.hari:
                    validateTempat();
                    break;
            }
        }
    }


    @Override
    //Tombol Kembali
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Activity_Utama.class);
        startActivityForResult(myIntent, 0);
        Simpan_Activity.this.finish();
        return true;
    }
}