package com.example.egi_fcb.pekepanukuik.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.egi_fcb.pekepanukuik.sqlite.PengingatPekerjaan;
import com.example.egi_fcb.pekepanukuik.R;

import java.util.List;

/**
 * Created by Egi FCB on 30/08/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<PengingatPekerjaan> mdataset;
    private ClickListener clicklistener = null;

    public RecyclerAdapter(List<PengingatPekerjaan> mdataset){
        this.mdataset = mdataset;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_card_viewdan_recycler_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tempat.setText(mdataset.get(position).getTxt_hari());
        holder.jam.setText(mdataset.get(position).getTxt_jam());
        holder.tanggal.setText(mdataset.get(position).getTxt_taggal());
        holder.pekerjaan.setText(mdataset.get(position).getTxt_pekerjaan());
        holder.id.setText(String.valueOf(mdataset.get(position).getId()));

        for(int b=0; b<=position; b++){
            int c = b+1;
            holder.imageView.setText(String.valueOf(c));
        }
    }


    @Override
    public int getItemCount() {
        return mdataset == null ? 0 : mdataset.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tempat, tanggal, jam, pekerjaan,id, jamakhir;
        TextView imageView;
        CardView cardView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            tempat = (TextView)itemView.findViewById(R.id.lvalamat);
            tanggal = (TextView)itemView.findViewById(R.id.lvtanggal);
            jam = (TextView)itemView.findViewById(R.id.lvjam);
            pekerjaan = (TextView)itemView.findViewById(R.id.lvpekerjaan);
            id = (TextView)itemView.findViewById(R.id.lvid2);

            imageView = (TextView) itemView.findViewById(R.id.image);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }
        }
    }
    public void setClickListener(ClickListener clicklistener ) {
        this.clicklistener = clicklistener ;
    }
}
