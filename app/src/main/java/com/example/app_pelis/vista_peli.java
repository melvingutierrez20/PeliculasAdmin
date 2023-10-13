package com.example.app_pelis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class vista_peli extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_peli);
        recyclerView = findViewById(R.id.recyler_view);
        setupRecyclerView();
    }

    void setupRecyclerView() {
        Query query = Utils.getCollectionReferenceForNotes().orderBy("nombre", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Peli> options = new FirestoreRecyclerOptions.Builder<Peli>().setQuery(query,Peli.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(options, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}