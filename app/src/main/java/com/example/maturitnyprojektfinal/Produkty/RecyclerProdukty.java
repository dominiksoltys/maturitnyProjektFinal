package com.example.maturitnyprojektfinal.Produkty;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maturitnyprojektfinal.R;
import com.example.maturitnyprojektfinal.RecyclerZoznamy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerProdukty extends AppCompatActivity implements RecAdapterP.onProduktClickListener{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, ZID, Nazov;

    RecyclerView recyclerView;
    TextView topNazov;
    ImageView image;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        ZID=getIntent().getStringExtra("ID");
        Nazov=getIntent().getStringExtra("Nazov");

        setContentView(R.layout.activity_recycler_view);

        view = new View (this);
        topNazov = findViewById(R.id.TopNazov);
        topNazov.setText(Nazov);
        recyclerView = findViewById(R.id.recyclerView);
        image = findViewById(R.id.recyclerImage);


        RecAdapterP recAdapterP = new RecAdapterP(this);
        recyclerView.setAdapter(recAdapterP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(recAdapterP);
    }
    public void getData(final RecAdapterP recAdapterP){
        fStore.collection("users").document(userId).collection("zoznamy")
                .document(ZID).collection("produkty")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){ Log.e("Yeet", e.getMessage()); }
                else {
                    ArrayList<Produkt> listik = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots) {
                        String Nazov = snapshot.getString("Nazov");
                        long Pocet = snapshot.getLong("Pocet");

                        listik.add(new Produkt(snapshot.getId(), Nazov, Pocet));
                    }
                    recAdapterP.setNewProdukt(listik);
                }
            }
        });
    }
    public void nazad(View view) {
        startActivity(new Intent(getApplicationContext(), RecyclerZoznamy.class));
    }
    public void add(View view) {
        Intent i = new Intent(getApplicationContext(), RecyclerPridat.class);
        i.putExtra("ID", ZID);
        i.putExtra("Nazov", Nazov);
        startActivity(i);

    }
    @Override
    public void onProduktClick(final String PID) {
        final EditText zmenaProdukt = new EditText(view.getContext());
        final AlertDialog.Builder zmenaProduktDialog = new AlertDialog.Builder(view.getContext());
        zmenaProduktDialog.setTitle("Zmena počtu");
        zmenaProduktDialog.setMessage("Zadajte nový počet");
        zmenaProduktDialog.setView(zmenaProdukt);

        zmenaProduktDialog.setPositiveButton("Pridať", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    long novyPocet =Long.parseLong(zmenaProdukt.getText().toString().trim());
                    fStore.collection("users").document(userId).collection("zoznamy")
                            .document(ZID).collection("produkty").document(PID).update("Pocet", novyPocet);
                    Toast.makeText(RecyclerProdukty.this, "Počet zmenený", Toast.LENGTH_SHORT).show();
                }
                catch (NumberFormatException e){
                    Toast.makeText(RecyclerProdukty.this, "Musí byť číslo", Toast.LENGTH_SHORT).show();}
            }
        });
        zmenaProduktDialog.setNegativeButton("Zrusit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {} //zatvorenie dialogu
        });
        zmenaProduktDialog.create().show();
    }
    public void onDeleteClick(String PID) {
        fStore.collection("users").document(userId).collection("zoznamy")
                .document(ZID).collection("produkty").document(PID).delete();
        Toast.makeText(this, "Produkt bol vymazaný", Toast.LENGTH_SHORT).show();
    }
}
