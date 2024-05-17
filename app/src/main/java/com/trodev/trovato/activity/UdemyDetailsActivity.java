package com.trodev.trovato.activity;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trodev.trovato.R;
import com.trodev.trovato.fragments.CourseFragment;

public class UdemyDetailsActivity extends AppCompatActivity {

    ImageView imageIv, back_btn;
    TextView nameTv, descriptionTv, languageTv, ratingTv;
    MaterialButton enrollBtn;
    String name, description, language, rating, image, date, time, link;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_udemy_details);

        getSupportActionBar().hide();

        nameTv = findViewById(R.id.name_TV);
        descriptionTv = findViewById(R.id.description_TV);
        languageTv = findViewById(R.id.language_TV);
        ratingTv = findViewById(R.id.rating_TV);
        enrollBtn = findViewById(R.id.enroll_btn);
        imageIv = findViewById(R.id.imageIv);
        back_btn = findViewById(R.id.back_btn);

        /*progressbar*/
        progressBar = findViewById(R.id.progressBar);


        // get data from adapter
        name = getIntent().getStringExtra("cname");
        description = getIntent().getStringExtra("cdes");
        language = getIntent().getStringExtra("clanguage");
        rating = getIntent().getStringExtra("crating");
        image = getIntent().getStringExtra("cimage");
        date = getIntent().getStringExtra("cdate");
        time = getIntent().getStringExtra("ctime");
        link = getIntent().getStringExtra("clink");

        /*set text on views*/
        nameTv.setText("Course name: "+name);
        descriptionTv.setText("Description:"+"\n"+description);
        languageTv.setText(language);
        ratingTv.setText(rating+ " ⭐⭐⭐");

        /*open google chrome apps click on enroll button*/
        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                // Start the activity
                startActivity(intent);
            }
        });


        /*how to back previous activity*/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*load image*/
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
        Picasso.get().load(image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                imageIv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                /*set image on image view*/
                imageIv.setImageBitmap(bitmap);
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