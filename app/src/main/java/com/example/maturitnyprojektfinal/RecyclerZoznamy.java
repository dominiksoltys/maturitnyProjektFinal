package com.example.maturitnyprojektfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maturitnyprojektfinal.Produkty.Produkt;
import com.example.maturitnyprojektfinal.Produkty.RecyclerProdukty;
import com.example.maturitnyprojektfinal.pojo.Zoznam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RecyclerZoznamy extends AppCompatActivity implements RecAdapter.onZoznamClickListener{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;

    RecyclerView recyclerView;
    TextView topNazov;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();

        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        image = findViewById(R.id.recyclerImage);
        topNazov = findViewById(R.id.TopNazov);
        topNazov.setText("Vaše zoznamy");

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
                        long Pocet = snapshot.getLong("Pocet");
                        double CenaK = snapshot.getDouble("CenaK");
                        double CenaT = snapshot.getDouble("CenaT");
                        double CenaL = snapshot.getDouble("CenaL");
                        double Cena ;
                        String Obchod;
                        if (CenaK<=CenaT&&CenaK<=CenaL){
                            Cena=CenaK;
                            Obchod="K";
                        }else if (CenaL<=CenaT&&CenaL<=CenaK){
                            Cena=CenaL;
                            Obchod="L";
                        }else if (CenaT<=CenaK&&CenaT<=CenaL){
                            Cena=CenaT;
                            Obchod="T";
                        }
                        else{
                            Cena=9999999;
                            Obchod="wut";
                        }
                        if(CenaK==0&&CenaL==0&&CenaT==0){
                            Cena=0;
                            Obchod="P";
                        }
                        Cena*=100;
                        Cena=Math.round(Cena);
                        Cena/=100;
                        listik.add(new Zoznam(snapshot.getId(), Nazov, Pocet, Cena, Obchod));
                    }
                    recAdapter.setNewData(listik);
                }
            }
        });
    }
    public void nazad(View view) {
        startActivity(new Intent(getApplicationContext(),drawerActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void add(View view){
        final EditText novyZoznam = new EditText(view.getContext());
        final AlertDialog.Builder novyZoznamDialog = new AlertDialog.Builder(view.getContext());
        novyZoznamDialog.setTitle("Zadajte názov");
        novyZoznamDialog.setMessage("Musí mať 3-15 znakov");
        novyZoznamDialog.setView(novyZoznam);

        novyZoznamDialog.setPositiveButton("Pridať", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (novyZoznam.getText().toString().trim().length()<3){
                    Toast.makeText(RecyclerZoznamy.this, "Názov musí mať viac ako 3 znaky", Toast.LENGTH_SHORT).show();
                }
                else if (novyZoznam.getText().toString().trim().length()>15){
                    Toast.makeText(RecyclerZoznamy.this, "Názov musí mať menej ako 15 znakov", Toast.LENGTH_SHORT).show();
                }
                else {
                    String novyNazov = novyZoznam.getText().toString().trim();
                    DocumentReference novyZoznam = fStore.collection("users").document(userId).collection("zoznamy").document();
                    Map<String,Object> zoznamy = new HashMap<>();
                    zoznamy.put("Nazov",novyNazov);
                    zoznamy.put("Pocet",0);
                    zoznamy.put("CenaK",0);
                    zoznamy.put("CenaT",0);
                    zoznamy.put("CenaL",0);
                    novyZoznam.set(zoznamy);
                }
            }
        });

        novyZoznamDialog.setNegativeButton("Zrusit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {} //zatvorenie dialogu
        });
        novyZoznamDialog.create().show();
    }
    @Override
    public void onZoznamClick(String ZID, String Nazov) {
        Intent i = new Intent(getApplicationContext(), RecyclerProdukty.class);
        i.putExtra("ID", ZID);
        i.putExtra("Nazov", Nazov);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
    @Override
    public void onDeleteClick(String ZID) {
        fStore.collection("users").document(userId).collection("zoznamy").document(ZID).delete();
        Toast.makeText(this, "Zoznam bol vymazaný", Toast.LENGTH_SHORT).show();
    }
}