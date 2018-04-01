package com.example.egi_fcb.pekepanukuik.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.R;

/**
 * Created by Egi FCB on 09/10/2016.
 */

public class Adapter_TerimaKasih extends ArrayAdapter<String>{

    private final Context appCompatActivity;
    private final String[] ucapan;
    private final String[] ucapan1;
    private final Integer[] makasih;
    
    public Adapter_TerimaKasih(AppCompatActivity appCompatActivity, String[] ucapan,String[] ucapan1, Integer[] makasih) {
        super(appCompatActivity, R.layout.costum_terima_kasih, ucapan);
        
        this.appCompatActivity = appCompatActivity;
        this.ucapan = ucapan;
        this.ucapan1 = ucapan1;
        this.makasih = makasih;
    }



    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)appCompatActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.costum_terima_kasih, parent, false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.tvucapan);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.tvucapan2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivterimakasih);


        txtTitle.setText(ucapan[position]);
        txtTitle1.setText(ucapan1[position]);
        imageView.setImageResource(makasih[position]);
        return rowView;
    }
}
