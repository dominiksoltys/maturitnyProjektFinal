package com.example.maturitnyprojektfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RecyclerZoznamy extends AppCompatActivity {
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    public CollectionReference recyclerRef = db.collection("zoznamy");

    public RecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        setUpRecyclerView();
    }

    public void setUpRecyclerView(){
        Query query = recyclerRef.orderBy("Nazov", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<RecDaco> options = new FirestoreRecyclerOptions.Builder<RecDaco>().setQuery(query, RecDaco.class).build();

        adapter = new RecAdapter(options);
        RecyclerView recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),drawerActivity.class));
    }
}
