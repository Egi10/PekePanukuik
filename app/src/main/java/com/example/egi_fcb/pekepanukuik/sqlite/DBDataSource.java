package com.example.egi_fcb.pekepanukuik.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Egi_FCB on 8/6/2016.
 */
public class DBDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    //ambil semua nama kolom
    private String[] allcolumns = {DBHelper.COLUMN_id, DBHelper.COLUMN_hari, DBHelper.COLUMN_tanggal,
                                   DBHelper.COLUMN_jam, DBHelper.COLUMN_jam_akhir, DBHelper.COLUMN_pekerjaan};

    //DBHelper diinstantiasi pada constructor
    public DBDataSource(Context context){
        dbHelper = new DBHelper(context);
    }

    //membuka sambungan baru ke database
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    //menutup sambungan ke database
    public void close(){
        dbHelper.close();
    }

    //Menthod Untuk Create/insert
    public PengingatPekerjaan createpake(String hari, String tanggal, String jam, String jam_akhir, String Pekerjaan){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_hari, hari);
        values.put(DBHelper.COLUMN_tanggal, tanggal);
        values.put(DBHelper.COLUMN_jam, jam);
        values.put(DBHelper.COLUMN_jam_akhir, jam_akhir);
        values.put(DBHelper.COLUMN_pekerjaan, Pekerjaan);

        long insertId = database.insert(DBHelper.TABLE_NAME, null, values);

        //Menyesuaikan ID
        Cursor cursor = database.query(DBHelper.TABLE_NAME,allcolumns,DBHelper.COLUMN_id+"="+insertId, null,null,null,null);

        //Pindah Ke Data Paling aPertama
        cursor.moveToFirst();

        //mengubah objek pada kursor pertama tadi ke dalam objek barang
        PengingatPekerjaan newpengingatPekerjaan = cursorTopake(cursor);

        cursor.close();

        return newpengingatPekerjaan;
    }

    private PengingatPekerjaan cursorTopake(Cursor cursor){
        //Buat Objek Barang Baru
        PengingatPekerjaan pengingatPekerjaan = new PengingatPekerjaan();

        //debug LOGCAT
        Log.v("info", "The getLONG"+cursor.getLong(0));
        Log.v("info", "The setLatLng"+cursor.getString(1)+","+cursor.getString(2));

        //Set Atribut
        pengingatPekerjaan.setId(cursor.getLong(0));
        pengingatPekerjaan.setTxt_hari(cursor.getString(1));
        pengingatPekerjaan.setTxt_taggal(cursor.getString(2));
        pengingatPekerjaan.setTxt_jam(cursor.getString(3));
        pengingatPekerjaan.setTxt_jam_akhir(cursor.getString(4));
        pengingatPekerjaan.setTxt_pekerjaan(cursor.getString(5));

        //kembali sebagai objek
        return pengingatPekerjaan;
    }

    //Mengambil Semua Data pekerjaan
    public ArrayList<PengingatPekerjaan> getAlPengingatPekerjaen(){
        ArrayList<PengingatPekerjaan> daftarkerja = new ArrayList<PengingatPekerjaan>();

        //select all sql query
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allcolumns, null, null, null, null, null);

        //pindah paling pertama
        cursor.moveToFirst();

        //jika masih ada data, masukan data pekerjaan
        while (!cursor.isAfterLast()){
            PengingatPekerjaan pengingatPekerjaan = cursorTopake(cursor);
            daftarkerja.add(pengingatPekerjaan);
            cursor.moveToNext();
        }

        //make sure to close the cursor
        cursor.close();
        return daftarkerja;
    }

    //Panggil Semua Data Dengan Costum ListView
    public Cursor getall()
    {
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allcolumns, null, null, null, null, null);
        return cursor;
    }

    //delete pekerjaan sesuai id
    public void deletepekerjaan(long id){
        String strFilter = "_id="+id;
        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }

    //update pekerjaan
    public void updatepekerjaan(PengingatPekerjaan pengingatPekerjaan){
        //ambil id pekerjaan
        String filter = "_id="+pengingatPekerjaan.getId();
        //memasukkan ke content values
        ContentValues contentValues = new ContentValues();
        //Memasukan data sesuai dengan kolom pada database
        contentValues.put(DBHelper.COLUMN_hari, pengingatPekerjaan.getTxt_hari());
        contentValues.put(DBHelper.COLUMN_tanggal, pengingatPekerjaan.getTxt_taggal());
        contentValues.put(DBHelper.COLUMN_jam, pengingatPekerjaan.getTxt_jam());
        contentValues.put(DBHelper.COLUMN_jam_akhir, pengingatPekerjaan.getTxt_jam_akhir());
        contentValues.put(DBHelper.COLUMN_pekerjaan, pengingatPekerjaan.getTxt_pekerjaan());
        //update query
        database.update(DBHelper.TABLE_NAME, contentValues, filter, null);
    }

    //panggil berdasarkan id
    public PengingatPekerjaan getpekerjaan(long id){
        PengingatPekerjaan pengingatPekerjaan = new PengingatPekerjaan();
        //Query
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allcolumns, "_id="+id, null, null, null, null);
        //Ambil Data Yang aPertama
        cursor.moveToFirst();
        //Masukan Data Cursor
        pengingatPekerjaan = cursorTopake(cursor);
        //tutup sambungan
        cursor.close();
        //return barang
        return pengingatPekerjaan;
    }

    /// MENGAMBIL 1 DATA BERDASARKAN ID
    public Cursor satuData(long id) {
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allcolumns, "_id="+id, null, null, null, null);
        return cursor;
    }

    //Table Sampah
    //Menthod Untuk Create/insert
    public PengingatPekerjaan createsampah(String hari, String tanggal, String jam, String jam_akhir, String Pekerjaan){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_hari, hari);
        values.put(DBHelper.COLUMN_tanggal, tanggal);
        values.put(DBHelper.COLUMN_jam, jam);
        values.put(DBHelper.COLUMN_jam_akhir, jam_akhir);
        values.put(DBHelper.COLUMN_pekerjaan, Pekerjaan);

        long insertId = database.insert(DBHelper.TABLE_NAME2, null, values);

        //Menyesuaikan ID
        Cursor cursor = database.query(DBHelper.TABLE_NAME2,allcolumns,DBHelper.COLUMN_id+"="+insertId, null,null,null,null);

        //Pindah Ke Data Paling aPertama
        cursor.moveToFirst();

        //mengubah pbjek pada kursor pertama tadi ke dalam objek barang
        PengingatPekerjaan newpengingatPekerjaan = cursorTopake(cursor);

        cursor.close();

        return newpengingatPekerjaan;
    }

    public Cursor  getPekerjaanListByKeyword(String search) {
        //Open connection to read only
        String selectQuery =  "SELECT  rowid as " +
                DBHelper.COLUMN_id + "," +
                DBHelper.COLUMN_hari + "," +
                DBHelper.COLUMN_jam + "," +
                DBHelper.COLUMN_jam_akhir + "," +
                DBHelper.COLUMN_pekerjaan + "," +
                DBHelper.COLUMN_tanggal +
                " FROM " + DBHelper.TABLE_NAME +
                " WHERE " + DBHelper.COLUMN_pekerjaan + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_jam + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_jam_akhir + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_hari + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_tanggal + "  LIKE  '%" +search + "%' ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    //SAMPAH
    //Mengambil Semua Data pekerjaan
    public ArrayList<PengingatPekerjaan> getAllSampah(){
        ArrayList<PengingatPekerjaan> daftarkerja = new ArrayList<PengingatPekerjaan>();

        //select all sql query
        Cursor cursor = database.query(DBHelper.TABLE_NAME2, allcolumns, null, null, null, null, null);

        //pindah paling pertama
        cursor.moveToFirst();

        //jika masih ada data, masukan data pekerjaan
        while (!cursor.isAfterLast()){
            PengingatPekerjaan pengingatPekerjaan = cursorTopake(cursor);
            daftarkerja.add(pengingatPekerjaan);
            cursor.moveToNext();
        }

        //make sure to close the cursor
        cursor.close();
        return daftarkerja;
    }

    //delete pekerjaan sesuai id
    public void deletesampah(long id){
        String strFilter = "_id="+id;
        database.delete(DBHelper.TABLE_NAME2, strFilter, null);
    }

    //delete pekerjaan semua
    public void deletesampahsemua(){
        database.delete(DBHelper.TABLE_NAME2, null, null);
    }

    //Pencarian
    public ArrayList<PengingatPekerjaan> getPekerjaanListBySampah(String search) {
        //Open connection to read only

        ArrayList<PengingatPekerjaan> daftarkerja = new ArrayList<PengingatPekerjaan>();

        String selectQuery =  "SELECT  rowid as " +
                DBHelper.COLUMN_id + "," +
                DBHelper.COLUMN_hari + "," +
                DBHelper.COLUMN_tanggal + "," +
                DBHelper.COLUMN_jam + "," +
                DBHelper.COLUMN_jam_akhir + "," +
                DBHelper.COLUMN_pekerjaan +
                " FROM " + DBHelper.TABLE_NAME2 +
                " WHERE " + DBHelper.COLUMN_pekerjaan + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_jam + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_jam_akhir + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_hari + "  LIKE  '%" +search + "%' "+" OR "+
                            DBHelper.COLUMN_tanggal + "  LIKE  '%" +search + "%' ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.getCount() < 1) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        //jika masih ada data, masukan data pekerjaan
        while (!cursor.isAfterLast()){
            PengingatPekerjaan pengingatPekerjaan = cursorTopake(cursor);
            daftarkerja.add(pengingatPekerjaan);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarkerja;
    }
    /// MENGAMBIL 1 DATA BERDASARKAN ID
    public Cursor satuDatasampah(long id) {
        Cursor cursor = database.query(DBHelper.TABLE_NAME2, allcolumns, "_id="+id, null, null, null, null);
        return cursor;
    }
}
