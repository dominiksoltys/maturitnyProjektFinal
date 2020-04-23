package com.example.maturitnyprojektfinal.Produkty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maturitnyprojektfinal.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdapterP extends RecyclerView.Adapter<RecAdapterP.ProduktViewHolder> {

    ArrayList<Produkt> listik = new ArrayList<>();
    private onProduktClickListener onProduktClickListener;
    public RecAdapterP(onProduktClickListener onProduktClickListener){
        this.onProduktClickListener=onProduktClickListener;
    }

    public void setNewProdukt(ArrayList<Produkt> listik){
        this.listik=listik;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProduktViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rec_row, parent, false);
        return new ProduktViewHolder(view, onProduktClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduktViewHolder holder, final int position) {
        holder.bindData(listik.get(position));
    }

    @Override
    public int getItemCount() {
        return listik.size();
    }

    public class ProduktViewHolder extends RecyclerView.ViewHolder{

        TextView TextNazov, TextPocet, TextCena;

        public ProduktViewHolder(@NonNull View itemView, RecAdapterP.onProduktClickListener onProduktClickListener) {
            super(itemView);
            TextNazov = itemView.findViewById(R.id.Nazov_text);
            TextPocet = itemView.findViewById(R.id.Pocet_text);
            TextCena = itemView.findViewById(R.id.Cena_text);
        }

        void bindData (final Produkt produkt){
            TextNazov.setText(produkt.getNazov());
            TextPocet.setText("                               Pocet: "+String.valueOf(produkt.getPocet()));
            TextCena.setText("");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProduktClickListener.onProduktClick(produkt.getPID());
                }
            });
        }

    }

    interface onProduktClickListener {
        void onProduktClick(String PID);
    }
}