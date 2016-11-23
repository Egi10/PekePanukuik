package com.example.egi_fcb.pekepanukuik.TampilanAwalGridView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.R;

/**
 * Created by Egi FCB on 23/09/2016.
 */

public class CostumAdapter extends ArrayAdapter<Object> {
    Context context;

    public CostumAdapter(Context context) {
        super(context,0);
        this.context = context;
    }

    public int getCount() {
        return 4;
    }

    public View getView(int posisi, View view, ViewGroup viewGroup){
        View row = view;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.costum_gridview,viewGroup,false );

            TextView textView = (TextView)row.findViewById(R.id.textimage);
            ImageView imageView = (ImageView)row.findViewById(R.id.image);

            switch (posisi){
                case 0 :
                    textView.setText("MASUKAN");
                    imageView.setImageResource(R.drawable.icon_input);
                break;

                case 1 :
                    textView.setText("GANTI");
                    imageView.setImageResource(R.drawable.icon_list_pekerjaan);
                    break;

                case 2 :
                    textView.setText("SAMPAH");
                    imageView.setImageResource(R.drawable.icon_sampah);
                break;

                case 3 :
                    textView.setText("KELUAR");
                    imageView.setImageResource(R.drawable.icon_keluar);
                break;
            }
        }
        return row;
    }
}
