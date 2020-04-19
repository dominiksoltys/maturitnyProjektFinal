package com.example.maturitnyprojektfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdapter extends FirestoreRecyclerAdapter<RecDaco, RecAdapter.RecHolder> {

    public RecAdapter(@NonNull FirestoreRecyclerOptions<RecDaco> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecHolder holder, int position, @NonNull RecDaco model) {
        holder.textNazov.setText(model.getNazov());
        holder.textPocet.setText(String.valueOf(model.getPocet()));
        holder.textCena.setText(String.valueOf(model.getCena()));
    }

    @NonNull
    @Override
    public RecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_row, parent, false);
        return new RecHolder(v);
    }

    class RecHolder extends RecyclerView.ViewHolder{
        TextView textNazov;
        TextView textPocet;
        TextView textCena;

        public RecHolder(@NonNull View itemView) {
            super(itemView);
                textNazov=itemView.findViewById(R.id.Nazov_text);
                textPocet=itemView.findViewById(R.id.Pocet_text);
                textCena=itemView.findViewById(R.id.Cena_text);
        }
    }
}
