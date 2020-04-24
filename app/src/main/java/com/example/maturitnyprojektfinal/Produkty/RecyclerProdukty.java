package com.example.maturitnyprojektfinal.Produkty;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import javax.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerProdukty extends AppCompatActivity implements RecAdapterP.onProduktClickListener{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, ZID;

    RecyclerView recyclerView;
    TextView topNazov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        ZID=getIntent().getStringExtra("ID");

        setContentView(R.layout.activity_recycler_view);

        topNazov = findViewById(R.id.TopNazov);
        topNazov.setText(getIntent().getStringExtra("Nazov"));
        recyclerView=findViewById(R.id.recyclerView);

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

    public void delete(View view){
        Toast.makeText(this, "Produkt odobraný", Toast.LENGTH_SHORT).show();
    }

    public void add(View view) {
        final EditText novyProdukt = new EditText(view.getContext());
        final AlertDialog.Builder novyProduktDialog = new AlertDialog.Builder(view.getContext());
        novyProduktDialog.setTitle("Pridanie produktu");
        novyProduktDialog.setMessage("Zadajte názov produktu");
        novyProduktDialog.setView(novyProdukt);

        novyProduktDialog.setPositiveButton("Pridať", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String novyNazov = novyProdukt.getText().toString().trim();
                Toast.makeText(RecyclerProdukty.this, novyNazov, Toast.LENGTH_SHORT).show();
            }
        });

        novyProduktDialog.setNegativeButton("Zrusit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //zatvorenie dialogu,navrat do Profil.java
            }
        });
        novyProduktDialog.create().show();
    }

    @Override
    public void onProduktClick(String PID) {
        Toast.makeText(this, PID, Toast.LENGTH_LONG).show();
    }
}
