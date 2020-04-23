package com.example.maturitnyprojektfinal.Produkty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.maturitnyprojektfinal.R;
import com.example.maturitnyprojektfinal.RecAdapter;
import com.example.maturitnyprojektfinal.RecyclerZoznamy;
import com.example.maturitnyprojektfinal.drawerActivity;
import com.example.maturitnyprojektfinal.pojo.Zoznam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerProdukty extends AppCompatActivity implements RecAdapterP.onProduktClickListener{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String ZID;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        ZID=getIntent().getStringExtra("ID");

        setContentView(R.layout.activity_recycler_view);

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

    @Override
    public void onProduktClick(String PID) {
        Toast.makeText(this, PID, Toast.LENGTH_LONG).show();
    }
}
