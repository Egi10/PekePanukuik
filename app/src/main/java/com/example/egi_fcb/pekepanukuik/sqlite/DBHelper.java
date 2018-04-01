package com.example.egi_fcb.pekepanukuik.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Egi_FCB on 8/6/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    //Deklarasikan Konstanta Yang Digunakan Pada Database

    //Nama Tabel
    public static final String TABLE_NAME = "pengingat_pekerjaan";
    public static final String TABLE_NAME2 = "sampah";
    //Isi Tabel1
    public static final String COLUMN_id = "_id";
    public static final String COLUMN_hari = "hari";
    public static final String COLUMN_tanggal = "tanggal";
    public static final String COLUMN_jam = "jam";
    public static final String COLUMN_jam_akhir = "jam_akhir";
    public static final String COLUMN_pekerjaan = "pekerjaan";

    //Nama Database
    private static final String db_name = "pakepanukuik.db";
    private static final int db_version = 2;

    //Perintah SQL untuk membuat tabel database baru

    private static final String db_buat = "create table "
            + TABLE_NAME + "("
            + COLUMN_id + " integer primary key autoincrement, "
            + COLUMN_hari + " text not null, "
            + COLUMN_tanggal + " text not null, "
            + COLUMN_jam + " text not null, "
            + COLUMN_jam_akhir + " text not null, "
            + COLUMN_pekerjaan+" text not null );";

    public DBHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query(TABLE_NAME));
        db.execSQL(query(TABLE_NAME2));
    }

    private static final String query(String Tabel) {
        return "create table "+Tabel+"("
                + COLUMN_id + " integer primary key autoincrement, "
                + COLUMN_hari + " text not null, "
                + COLUMN_tanggal + " text not null, "
                + COLUMN_jam + " text not null, "
                + COLUMN_jam_akhir + " text not null, "
                + COLUMN_pekerjaan + " text not null);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),"Upgrading database from version"+oldVersion+"to"
                +newVersion+",which will destory all old data");
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME2);
        onCreate(db);
    }
}
