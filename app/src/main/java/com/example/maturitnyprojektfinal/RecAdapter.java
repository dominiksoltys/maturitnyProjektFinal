package com.example.maturitnyprojektfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maturitnyprojektfinal.pojo.Zoznam;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    ArrayList<Zoznam> listik = new ArrayList<>();
    public RecAdapter(){}

    public void setNewData(ArrayList<Zoznam> listik){
        this.listik=listik;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rec_row, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {
        holder.TextNazov.setText(listik.get(position).getNazov());
        holder.TextPocet.setText(String.valueOf(listik.get(position).getPocet()));
        holder.TextCena.setText(String.valueOf(listik.get(position).getCena()));
    }

    @Override
    public int getItemCount() {
        return listik.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{

        TextView TextNazov, TextPocet, TextCena;

        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            TextNazov = itemView.findViewById(R.id.Nazov_text);
            TextPocet = itemView.findViewById(R.id.Pocet_text);
            TextCena = itemView.findViewById(R.id.Cena_text);
        }
    }
}
