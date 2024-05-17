package com.trodev.trovato.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trodev.trovato.R;

public class EnvatoDetailsActivity extends AppCompatActivity {

    private TextView pname_TV, pcode_TV, pprice_TV, pdate_TV,pTime_TV, plicense_TV, sup_TV, pdes_TV, filesize_TV;
    ImageView imageView;
    String pname, pcode, pdate, plicense, pdes, pfilesize, pappsup, ptime, pvideo, pprice, pimage;
    LinearLayout plive_ll;
    ProgressBar progressBar;
    CardView imageCard;
    Button pay_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_envato_details);

        /*action bar title*/
        getSupportActionBar().setTitle("Products Details");

        // get data from adapter
        pname = getIntent().getStringExtra("pname");
        pcode = getIntent().getStringExtra("pcode");
        pdate = getIntent().getStringExtra("pdate");
        plicense = getIntent().getStringExtra("plicense");
        pdes = getIntent().getStringExtra("pdes");
        pfilesize = getIntent().getStringExtra("pfilesize");
        pappsup = getIntent().getStringExtra("pappsup");
        ptime = getIntent().getStringExtra("ptime");
        pvideo = getIntent().getStringExtra("pvideo");
        pprice = getIntent().getStringExtra("pprice");
        pimage = getIntent().getStringExtra("pimage");


        // pdfView = findViewById(R.id.pdfView);
        plive_ll = findViewById(R.id.plive_ll);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageIv);
        // imageCard = findViewById(R.id.imageCard);

        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);


        /*init views*/
        pname_TV = findViewById(R.id.pname_TV);
        pcode_TV = findViewById(R.id.pcode_TV);
        pprice_TV = findViewById(R.id.pprice_TV);
        pdate_TV = findViewById(R.id.pdate_TV);
        pTime_TV = findViewById(R.id.pTime_TV);
        plicense_TV = findViewById(R.id.plicense_TV);
        sup_TV = findViewById(R.id.sup_TV);
        pdes_TV = findViewById(R.id.pdes_TV);
        filesize_TV = findViewById(R.id.filesize_TV);

        imageView = findViewById(R.id.imageIv);

        pay_btn = findViewById(R.id.pay_btn);

        pay_btn.setText("Checkout Now "+ pprice );

        /*set on click listener*/
        plive_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(pvideo));
                startActivity(myWebLink);
            }
        });

        /*set data on activities views*/
        pname_TV.setText(pname);
        pcode_TV.setText(pcode);
        pprice_TV.setText(pprice);
        pdate_TV.setText(pdate);
        pTime_TV.setText(ptime);
        plicense_TV.setText(plicense);
        sup_TV.setText(pappsup);
        pdes_TV.setText(pdes);
        filesize_TV.setText(pfilesize);

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EnvatoDetailsActivity.this, CheckOutActivity.class);

                intent.putExtra("pname", pname);
                intent.putExtra("plicense", plicense);
                intent.putExtra("pprice", pprice);
                intent.putExtra("pcode", pcode);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });


        simulateDataLoading();
    }

    private void simulateDataLoading() {
        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Simulate data loading process
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Data loading completed, hide progress bar
                progressBar.setVisibility(View.GONE);
                loadImage();
            }
        }, 3000); // Simulate loading for 3 seconds
    }

    private void loadImage() {

        /*get image on database and it's a fit image*/
        Picasso.get().load(pimage).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                /*set image on image view*/
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}