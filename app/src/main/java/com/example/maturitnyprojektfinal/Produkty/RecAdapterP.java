package com.example.maturitnyprojektfinal.Produkty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maturitnyprojektfinal.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdapterP extends RecyclerView.Adapter<RecAdapterP.ProduktViewHolder> implements Filterable {

    ArrayList<Produkt> listik = new ArrayList<>();
    ArrayList<Produkt> listikall;
    private onProduktClickListener onProduktClickListener;
    public RecAdapterP(onProduktClickListener onProduktClickListener){
        this.onProduktClickListener=onProduktClickListener;

    }

    public void setNewProdukt(ArrayList<Produkt> listik){
        this.listik=listik;
        this.listikall = new ArrayList<>(listik);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProduktViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rec_row, parent, false);
        return new ProduktViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProduktViewHolder holder, final int position) {
        holder.bindData(listik.get(position));
    }
    @Override
    public int getItemCount() {
        return listik.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Produkt> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(listikall);

            }else {
                for(Produkt produkt:listikall){
                    if(produkt.toString().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(produkt);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listik.clear();
            listik.addAll((Collection<? extends Produkt>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ProduktViewHolder extends RecyclerView.ViewHolder{

        TextView TextNazov, TextPocet;
        ImageView imageView;
        public ImageView mDeleteImage;

        public ProduktViewHolder(@NonNull View itemView) {
            super(itemView);
            TextNazov = itemView.findViewById(R.id.Nazov_text);
            TextPocet = itemView.findViewById(R.id.Pocet_text);
            imageView = itemView.findViewById(R.id.recyclerImage);
            mDeleteImage = itemView.findViewById(R.id.imageDelete);
        }
        void bindData (final Produkt produkt){
            TextNazov.setText(produkt.getNazov());
            if (produkt.getPocet()==-1){TextPocet.setText("");}
            else {TextPocet.setText("Pocet: "+String.valueOf(produkt.getPocet()));}
            if ((produkt.getPID()).equals(produkt.getNazov())){
                imageView.setImageResource(R.drawable.produkt_side_g);
                mDeleteImage.setImageResource(R.drawable.empty);}
            else{
                imageView.setImageResource(R.drawable.produkt_side_o);
                mDeleteImage.setImageResource(R.drawable.ic_clear);}


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProduktClickListener.onProduktClick(produkt.getPID());
                }
            });
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setImageResource(R.drawable.produkt_side_r);
                    onProduktClickListener.onDeleteClick(produkt.getPID());
                }
            });
        }
    }
    interface onProduktClickListener {
        void onProduktClick(String PID);
        void onDeleteClick(String PID);
    }
}