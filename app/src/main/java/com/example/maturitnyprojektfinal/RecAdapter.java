package com.example.maturitnyprojektfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    String s1[], s2[], s3[];
    Context ct;
    public RecAdapter(Context ct, String s1[], String s2[], String s3[]){
        this.ct=ct;
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.rec_row, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {
        holder.TextNazov.setText(s1[position]);
        holder.TextPocet.setText(s2[position]);
        holder.TextCena.setText(s3[position]);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{

        TextView TextNazov, TextPocet, TextCena;
        ConstraintLayout RecLayout;

        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            TextNazov = itemView.findViewById(R.id.Nazov_text);
            TextPocet = itemView.findViewById(R.id.Pocet_text);
            TextCena = itemView.findViewById(R.id.Cena_text);
            RecLayout = itemView.findViewById(R.id.recLayout);
        }
    }
}
