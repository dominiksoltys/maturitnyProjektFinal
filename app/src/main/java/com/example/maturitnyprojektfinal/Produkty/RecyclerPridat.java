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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
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

public class RecyclerPridat extends AppCompatActivity implements RecAdapterP.onProduktClickListener {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, ZID, Nazov;

    View view;
    RecyclerView recyclerView;
    TextView topNazov;
    ImageView image;
    ImageView imageDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        ZID = getIntent().getStringExtra("ID");
        Nazov = getIntent().getStringExtra("Nazov");

        setContentView(R.layout.activity_recycler_view);

        view = new View(this);
        topNazov = findViewById(R.id.TopNazov);
        topNazov.setText("Pridanie");
        imageDelete=findViewById(R.id.imageDelete);
        //imageDelete.setWillNotDraw(true);    toto crashne apku :(
        recyclerView = findViewById(R.id.recyclerView);
        image = findViewById(R.id.recyclerImage);


        RecAdapterP recAdapterP = new RecAdapterP(this);
        recyclerView.setAdapter(recAdapterP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(recAdapterP);
    }
    public void getData(final RecAdapterP recAdapterP) {
        final ArrayList<Produkt> zoznamlistik = new ArrayList<>();
        fStore.collection("users").document(userId).collection("zoznamy")
                .document(ZID).collection("produkty").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Chyba ", e.getMessage());
                } else {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String Nazov = snapshot.getString("Nazov");
                        long Pocet = snapshot.getLong("Pocet");
                        zoznamlistik.add(new Produkt(snapshot.getId(), Nazov, Pocet)); //Ziskanie udajov zo zoznamu
                    }
                }
            }
        });
        final ArrayList<Produkt> listik = new ArrayList<>();
        fStore.collection("produkty").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Chyba ", e.getMessage());
                } else {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String Nazov = snapshot.getId();
                        long Pocet = -1;
                        boolean uzMame=false;
                        for (Produkt p:zoznamlistik) {
                            if (p.getNazov().trim().equals(Nazov.trim())){
                                uzMame=true;
                                break;
                            }
                        }
                        if (!uzMame){
                            listik.add(new Produkt(snapshot.getId(), Nazov, Pocet));} //Ziskanie udajov o vsetkych produktoch
                    }
                }
                recAdapterP.setNewProdukt(listik);
            }
        });
    }
    public void nazad(View view) {
        Intent i = new Intent(getApplicationContext(), RecyclerProdukty.class);
        i.putExtra("ID", ZID);
        i.putExtra("Nazov", Nazov);
        startActivity(i);
    }
    public void add(View view) {
        Toast.makeText(this, "Stlač produkt pre pridanie", Toast.LENGTH_LONG).show();
    } //nebude tam
    @Override
    public void onProduktClick(final String PID) {
        final EditText novyProdukt = new EditText(view.getContext());
        final AlertDialog.Builder novyProduktDialog = new AlertDialog.Builder(view.getContext());
        novyProduktDialog.setTitle(PID);
        novyProduktDialog.setMessage("Zadajte počet");
        novyProdukt.setText("1");
        novyProduktDialog.setView(novyProdukt);


        novyProduktDialog.setPositiveButton("Pridať", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    final long novyPocet =Long.parseLong(novyProdukt.getText().toString().trim());
                    if (novyPocet<1){
                        Toast.makeText(RecyclerPridat.this, "Počet musí byť väčší ako 0", Toast.LENGTH_SHORT).show();}
                    else {Map<String,Object> produkt = new HashMap<>();
                        produkt.put("Nazov", PID);
                        produkt.put("Pocet",novyPocet);
                        fStore.collection("users").document(userId).collection("zoznamy")
                                .document(ZID).collection("produkty").document().set(produkt);
                        String ye;
                        if (novyPocet==1) ye="Produkt pridaný";
                        else ye="Produkty pridané";
                        Toast.makeText(RecyclerPridat.this, ye, Toast.LENGTH_SHORT).show();

                        final long[] Pocet = new long[1];
                        final double[] Ceny = new double[3];

                        fStore.collection("users").document(userId).collection("zoznamy")
                                .document(ZID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Pocet[0] = documentSnapshot.getLong("Pocet");
                                Ceny[0] = documentSnapshot.getDouble("CenaK");
                                Ceny[1] = documentSnapshot.getDouble("CenaL");
                                Ceny[2] = documentSnapshot.getDouble("CenaT");
                            }
                        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(Task<DocumentSnapshot> task) {
                        fStore.collection("produkty").document(PID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Pocet[0]++;
                                Ceny[0] += (documentSnapshot.getDouble("cenakaufland")*novyPocet);
                                Ceny[1] += (documentSnapshot.getDouble("cenalidl")*novyPocet);
                                Ceny[2] += (documentSnapshot.getDouble("cenatesco")*novyPocet);
                                Map<String,Object> zoznam = new HashMap<>();
                                zoznam.put("Pocet", Pocet[0]);
                                zoznam.put("CenaK", Ceny[0]);
                                zoznam.put("CenaL", Ceny[1]);
                                zoznam.put("CenaT", Ceny[2]);
                                fStore.collection("users").document(userId).collection("zoznamy")
                                        .document(ZID).update(zoznam);
                            }
                        });
                            }
                        });
                    }}
                catch (NumberFormatException e){
                    Toast.makeText(RecyclerPridat.this, "Musí byť celé číslo", Toast.LENGTH_SHORT).show();}

            }
        });

        novyProduktDialog.setNegativeButton("Zrusit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {} //zatvorenie dialogu
        });
        novyProduktDialog.create().show();
    }

    @Override
    public void onDeleteClick(String PID) {} //tiez nebude tam
}