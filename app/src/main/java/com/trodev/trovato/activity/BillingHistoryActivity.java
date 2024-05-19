package com.trodev.trovato.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trodev.trovato.models.BillModels;
import com.trodev.trovato.R;
import com.trodev.trovato.adapters.BillHistoryAdapter;

import java.util.ArrayList;

public class BillingHistoryActivity extends AppCompatActivity {

    ImageView back_btn;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<BillModels> list;
    BillHistoryAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_billing_history);

        /*hide title bar*/
        getSupportActionBar().hide();

        /*init view*/
        back_btn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);


        /*set on click*/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("user_payment");

        /*init views*/
        recyclerView = findViewById(R.id.recyclerView);

        /*create methods*/
        load_data();

    }

    private void load_data() {

        Query query = reference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                if (!dataSnapshot.exists()) {

                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(BillingHistoryActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                } else {

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        BillModels data = snapshot.getValue(BillModels.class);
                        list.add(0, data);

                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(BillingHistoryActivity.this));
                    adapter = new BillHistoryAdapter(list, BillingHistoryActivity.this);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(BillingHistoryActivity.this, "History found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}