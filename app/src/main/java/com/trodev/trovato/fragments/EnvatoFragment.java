package com.trodev.trovato.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trodev.trovato.adapters.EnvatoAdapter;
import com.trodev.trovato.models.EnvatoModels;
import com.trodev.trovato.R;
import com.trodev.trovato.models.UdemyModels;

import java.util.ArrayList;

public class EnvatoFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<EnvatoModels> model;
    EnvatoAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    SearchView search_view;

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

        /*search view implement*/
        search_view = view.findViewById(R.id.search_btn);
        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        model = new ArrayList<>();

        // database = FirebaseDatabase.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("envato_elements");

        adapter = new EnvatoAdapter(model, getContext());

        /*linear layout manager*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        /*gridview layout manager*/
/*       GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
         recyclerView.setLayoutManager(layoutManager);*/

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

    public void filterList(String text) {

        ArrayList<EnvatoModels> filteredList = new ArrayList<>();
        for(EnvatoModels envatoModels : model )
        {
            if(envatoModels.getPname().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(envatoModels);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }

        if(filteredList.isEmpty())
        {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else
        {
            adapter.setFilteredList(filteredList);
        }


    }
}