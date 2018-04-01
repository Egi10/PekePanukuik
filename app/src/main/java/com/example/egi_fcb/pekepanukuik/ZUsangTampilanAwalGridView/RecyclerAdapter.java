package com.example.egi_fcb.pekepanukuik.ZUsangTampilanAwalGridView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.Package_Tampilan_Trash.Activity_Trash;
import com.example.egi_fcb.pekepanukuik.Package_Tampilan_Trash.ClickListener;
import com.example.egi_fcb.pekepanukuik.ZUsangDaftarListView.Daftar_Costum_ListView;
import com.example.egi_fcb.pekepanukuik.Package_Simpan.Simpan_Activity;
import com.example.egi_fcb.pekepanukuik.R;

import java.util.List;

/**
 * Created by egi_fcb on 1/26/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ClickListener clicklistener = null;
    List<Tampilan_Awal.User> values;
    Context context1;
    private Tampilan_Awal clickListener;

    public RecyclerAdapter(Context context, List<Tampilan_Awal.User> userInformation) {
        context1 = context;
        this.values = userInformation;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.costum_tampilan_awal, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.textimage.setText(values.get(position).getProfileName());
        holder.image.setImageResource(values.get(position).getImageResourceId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Intent intent = new Intent(v.getContext(), Simpan_Activity.class);
                    v.getContext().startActivity(intent);
                }
                else if(position == 1){
                    Intent intent = new Intent(v.getContext(), Daftar_Costum_ListView.class);
                    v.getContext().startActivity(intent);
                }
                else if(position == 2){
                    Intent intent = new Intent(v.getContext(), Activity_Trash.class);
                    v.getContext().startActivity(intent);
                }
                else if(position == 3){
                    //Toast.makeText(context1,"Makan"+position, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alBuilder = new AlertDialog.Builder(v.getContext());
                    alBuilder.setMessage("Apakah Anda Ingin Keluar ?");
                    alBuilder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent keluar = new Intent(Intent.ACTION_MAIN);
                            keluar.addCategory(Intent.CATEGORY_HOME);
                            keluar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context1.startActivity(keluar);
                        }
                    }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView  textimage,textid;
        ImageView image;
        CardView cardView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            textimage = (TextView)itemView.findViewById(R.id.textimage);
            image = (ImageView)itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }
        }
    }
}
