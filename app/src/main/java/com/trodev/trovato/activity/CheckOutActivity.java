package com.trodev.trovato.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trodev.trovato.R;

public class CheckOutActivity extends AppCompatActivity {

    TextView productCode_TV, license_TV, customername_TV,mobile_TV, productname_TV, price_TV, total_price_TV, email_TV ;
    String pcode, license, productname, price;
    ImageView back_btn;
    MaterialButton payment_btn;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    String username, usermobile, useremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_out);

        getSupportActionBar().hide();

        /*database initialize*/
        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Registered_User");

        /*init views*/
        productCode_TV = findViewById(R.id.productCode_TV);
        license_TV = findViewById(R.id.license_TV);
        customername_TV = findViewById(R.id.customername_TV);
        mobile_TV = findViewById(R.id.mobile_TV);
        productname_TV = findViewById(R.id.productname_TV);
        price_TV = findViewById(R.id.price_TV);
        total_price_TV = findViewById(R.id.total_price_TV);
        email_TV = findViewById(R.id.email_TV);
        back_btn = findViewById(R.id.back_btn);
        payment_btn = findViewById(R.id.payment_btn);

        /*get data*/
        pcode = getIntent().getStringExtra("pcode");
        license = getIntent().getStringExtra("plicense");
        productname = getIntent().getStringExtra("pname");
        price = getIntent().getStringExtra("pprice");

        /*set data on views*/
        productCode_TV.setText(pcode);
        license_TV.setText(license);
        productname_TV.setText(productname);
        price_TV.setText(price);
        total_price_TV.setText(price);

        /*how to back previous activity*/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check until required data get
                for (DataSnapshot ds : snapshot.getChildren()){
                    //get data from database
                    username = ""+ ds.child("uname").getValue();
                    useremail = ""+ ds.child("email").getValue();
                    usermobile = ""+ ds.child("num").getValue();


                    //set data on views
                    customername_TV.setText(username);
                    email_TV.setText(useremail);
                    mobile_TV.setText(usermobile);

//                    try {
//                        Picasso.get().load(image).into(avatarTv);
//                        progress_circular.setVisibility(View.INVISIBLE);
//                    } catch (Exception e){
//                        Picasso.get().load(R.drawable.me).into(avatarTv);
//                    }

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


        /*payment button*/
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, PaymentActivity.class);
                intent.putExtra("c_price", price);
                intent.putExtra("c_pname", productname);
                intent.putExtra("c_name", username);
                intent.putExtra("c_mobile", usermobile);
                intent.putExtra("c_pcode", pcode);
                intent.putExtra("c_email", useremail);
                startActivity(intent);
                Toast.makeText(CheckOutActivity.this, "Processing...", Toast.LENGTH_LONG).show();
            }
        });

    }
}