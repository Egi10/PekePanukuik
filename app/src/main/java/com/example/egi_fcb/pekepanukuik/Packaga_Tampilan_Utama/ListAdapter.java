package com.example.egi_fcb.pekepanukuik.Packaga_Tampilan_Utama;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.Package_Database.DBDataSource;
import com.example.egi_fcb.pekepanukuik.R;

/**
 * Created by Egi FCB on 04/09/2016.
 */
public class ListAdapter extends CursorAdapter {

    LayoutInflater ly;
    DBDataSource handler;


    public ListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        handler = new DBDataSource(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = ly.inflate(R.layout.costum_listview,parent,false);
        holder h = new holder();
        h.image = (TextView)v.findViewById(R.id.image);
        h.lvpekerjaan = (TextView)v.findViewById(R.id.lvpekerjaan);
        h.lvalamat = (TextView)v.findViewById(R.id.lvalamat);
        h.lvtanggal = (TextView)v.findViewById(R.id.lvtanggal);
        h.lvjam = (TextView)v.findViewById(R.id.lvjam);
        h.lvid = (TextView)v.findViewById(R.id.lvid);
        v.setTag(h);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final holder h = (holder)view.getTag();

        //String tes = cursor.getString(cursor.getColumnIndex("pekerjaan"));
        //String tes1 = tes.substring(0,50);
        //h.lvpekerjaan.setText(tes1);

        h.lvpekerjaan.setText(cursor.getString(cursor.getColumnIndex("pekerjaan")));
        h.lvalamat.setText(cursor.getString(cursor.getColumnIndex("hari")));
        h.lvtanggal.setText(cursor.getString(cursor.getColumnIndex("tanggal")));
        h.lvjam.setText(cursor.getString(cursor.getColumnIndex("jam")));
        h.lvid.setText(cursor.getString(cursor.getColumnIndex("_id")));

        int a =cursor.getPosition();

        for(int b=0; b<=a; b++){
            int c = b+1;
            h.image.setText(String.valueOf(c));
        }
    }

    static class holder{
        TextView lvpekerjaan;
        TextView lvalamat;
        TextView lvtanggal;
        TextView lvjam;
        TextView lvid;
        TextView image;
    }
}