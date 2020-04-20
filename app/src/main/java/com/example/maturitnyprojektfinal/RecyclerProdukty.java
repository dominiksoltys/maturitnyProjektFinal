package com.example.maturitnyprojektfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class RecyclerProdukty extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[], s2[], s3[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView=findViewById(R.id.recyclerView);

        s1=getResources().getStringArray(R.array.NazvyProduktov);
        s2=getResources().getStringArray(R.array.PocetProduktov);
        s3=getResources().getStringArray(R.array.CenyProduktov);

        RecAdapter recAdapter = new RecAdapter(this, s1, s2, s3);
        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void nazad(View view) {
        startActivity(new Intent(getApplicationContext(),drawerActivity.class));
    }
}
