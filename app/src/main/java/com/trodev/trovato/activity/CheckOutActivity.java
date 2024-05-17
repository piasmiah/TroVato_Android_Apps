package com.trodev.trovato.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.trodev.trovato.R;

public class CheckOutActivity extends AppCompatActivity {

    TextView productCode_TV, license_TV, customername_TV,mobile_TV, productname_TV, price_TV, total_price_TV ;
    String pcode, license, customername, mobile, productname, price, total_price;
    ImageView back_btn;
    MaterialButton payment_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_out);

        getSupportActionBar().hide();

        productCode_TV = findViewById(R.id.productCode_TV);
        license_TV = findViewById(R.id.license_TV);
        customername_TV = findViewById(R.id.customername_TV);
        mobile_TV = findViewById(R.id.mobile_TV);
        productname_TV = findViewById(R.id.productname_TV);
        price_TV = findViewById(R.id.price_TV);
        total_price_TV = findViewById(R.id.total_price_TV);
        back_btn = findViewById(R.id.back_btn);
        payment_btn = findViewById(R.id.payment_btn);


        pcode = getIntent().getStringExtra("pcode");
        license = getIntent().getStringExtra("plicense");
        productname = getIntent().getStringExtra("pname");
        price = getIntent().getStringExtra("pprice");

        productCode_TV.setText(pcode);
        license_TV.setText(license);
        productname_TV.setText(productname);
        price_TV.setText(price);

        /*how to back previous activity*/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CheckOutActivity.this, "Processing...", Toast.LENGTH_LONG).show();
            }
        });

    }
}