package com.example.maturitnyprojektfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.maturitnyprojektfinal.pojo.Zoznam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class RecyclerZoznamy extends AppCompatActivity implements RecAdapter.onZoznamClickListener{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        setContentView(R.layout.activity_recycler_view);

        recyclerView=findViewById(R.id.recyclerView);

        RecAdapter recAdapter = new RecAdapter(this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(recAdapter);
    }
    
    public void getData(final RecAdapter recAdapter){
        fStore.collection("users").document(userId).collection("zoznamy").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){ Log.e("Yeet", e.getMessage()); }
                else {
                    ArrayList<Zoznam> listik = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots) {
                        String Nazov = snapshot.getString("Nazov");
                        double Pocet = snapshot.getDouble("Pocet");
                        double Cena = snapshot.getDouble("Cena");

                        listik.add(new Zoznam(snapshot.getId(), Nazov, Pocet, Cena));
                    }
                    recAdapter.setNewData(listik);
                }
            }
        });
    }
    
    public void nazad(View view) {
        startActivity(new Intent(getApplicationContext(),drawerActivity.class));
    }

    @Override
    public void onZoznamClick(String ZID) {
        Toast.makeText(this, ZID, Toast.LENGTH_LONG).show();
    }
}
