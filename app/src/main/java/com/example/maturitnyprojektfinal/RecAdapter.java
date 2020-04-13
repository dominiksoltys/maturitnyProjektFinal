package com.example.maturitnyprojektfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    String s1[], i1[];
    Context ct;
    public RecAdapter(Context ct, String s1[], String i1[]){
        this.ct=ct;
        this.s1=s1;
        this.i1=i1;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.rec_row, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        holder.TextNazov.setText(s1[position]);
        holder.TextCena.setText(i1[position]);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{

        TextView TextNazov, TextCena;

        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            TextNazov = itemView.findViewById(R.id.Nazov_text);
            TextCena = itemView.findViewById(R.id.Cena_text);
        }
    }
}
