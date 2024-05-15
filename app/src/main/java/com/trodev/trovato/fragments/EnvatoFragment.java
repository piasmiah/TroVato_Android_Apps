package com.trodev.trovato.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trodev.trovato.adapters.EnvatoAdapter;
import com.trodev.trovato.models.EnvatoModels;
import com.trodev.trovato.R;

import java.util.ArrayList;

public class EnvatoFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<EnvatoModels> model;
    EnvatoAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;


    public EnvatoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_envato, container, false);

        recyclerView =view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        model = new ArrayList<>();

        // database = FirebaseDatabase.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("envato_elements");

        adapter = new EnvatoAdapter(model, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    progressBar.setVisibility(View.GONE);

                    EnvatoModels allJobModel = dataSnapshot.getValue(EnvatoModels.class);
                    model.add(0, allJobModel);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*database synced*/
        reference.keepSynced(true);

        return view;
    }
}