package com.trodev.trovato.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trodev.trovato.activity.BillingHistoryActivity;
import com.trodev.trovato.R;
import com.trodev.trovato.activity.SignInActivity;
import com.trodev.trovato.models.UserStatus;

public class ProfileFragment extends Fragment {
    LinearLayout btn_logout, btn_cngpassword, cart_btn;
    ImageView avatarTv, coverTv;
    TextView nameTv, emailTv, user_status, numberTv;
    FloatingActionButton fab;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //StorageReference storageReference;
    ProgressBar progress_circular;
    DatabaseReference reference, ref;
    String userID;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //init firebase
        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Registered_User");

        //init views
        avatarTv= view.findViewById(R.id.avatarTv);
        nameTv= view.findViewById(R.id.nameTv);
        emailTv= view.findViewById(R.id.emailTv);
        numberTv= view.findViewById(R.id.numberTv);
        progress_circular= view.findViewById(R.id.progress_circular);
        user_status = view.findViewById(R.id.user_status);

        /*linear layout*/
        cart_btn = view.findViewById(R.id.cart_btn);

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BillingHistoryActivity.class));
            }
        });

        progress_circular.setVisibility(View.VISIBLE);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check until required data get
                for (DataSnapshot ds : snapshot.getChildren()){
                    //get data from database
                    String username = "Name: "+ ds.child("uname").getValue();
                    String email = "Email: "+ ds.child("email").getValue();
                    String number = "Mobile number: "+ ds.child("num").getValue();
                    String image = ""+ ds.child("image").getValue();

                    /*String cover = ""+ ds.child("cover").getValue();*/

                    //set data on views
                    nameTv.setText(username);
                    emailTv.setText(email);
                    numberTv.setText(number);
                    try {
                        Picasso.get().load(image).into(avatarTv);
                        progress_circular.setVisibility(View.INVISIBLE);
                    } catch (Exception e){
                        Picasso.get().load(R.drawable.me).into(avatarTv);
                    }

//                    try {
//                        /*Picasso.get().load(cover).into(coverTv);*/
//                        Picasso.get().load(cover).into(coverTv);
//                    } catch (Exception e){
//                        Picasso.get().load(R.drawable.add_image).into(coverTv);
//                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_cngpassword= view.findViewById(R.id.btn_cngpassword);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_cngpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getContext(), ChangePasswordActivity.class));
                Toast.makeText(getContext(), "Password Recovery", Toast.LENGTH_SHORT).show();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignInActivity.class));
                Toast.makeText(getContext(), "log-out successful", Toast.LENGTH_SHORT).show();
                getActivity().finishAffinity();
            }
        });

        /*status call*/
        status_call();

        /*database location and get user uid*/
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        return view;

    }

    private void status_call() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        /*show single value from database*/
        ref = FirebaseDatabase.getInstance().getReference("User_Status");
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserStatus userProfile = snapshot.getValue(UserStatus.class);
                if (userProfile != null) {

                    String status = userProfile.getStatus();
                    user_status.setText("Status: " + status);

                    if (status.equals("inactive")) {
                        user_status.setTextColor(Color.parseColor("#FF0004"));
                        // user_image.setColorFilter(getContext().getResources().getColor(R.color.green));
                    } else if (status.equals("active")) {
                        user_status.setTextColor(Color.parseColor("#008937"));
                        //user_image.setColorFilter(getContext().getResources().getColor(R.color.green));
                    }

                    Toast.makeText(getContext(), "You are " + status + " in our network", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
}