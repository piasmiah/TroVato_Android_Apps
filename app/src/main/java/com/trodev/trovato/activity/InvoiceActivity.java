package com.trodev.trovato.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.WriterException;

import com.google.android.material.card.MaterialCardView;
import com.trodev.trovato.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InvoiceActivity extends AppCompatActivity {

    TextView invoice_tv, date_tv, customer_name_tv, customer_email_tv, customer_mobile_tv, transaction_id_tv, product_code_tv, product_price_tv, total_price_tv;
    String invoice, date, customer_name, customer_email, customer_mobile, transaction, product_code, product_price;
    final static int REQUEST_CODE = 1232;
    MaterialCardView infoLl;
    MaterialButton download_btn;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);

        getSupportActionBar().hide();

        back_btn = findViewById(R.id.back_btn);

        /*set on click*/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*init cardview*/
        infoLl = findViewById(R.id.infoLl);
        download_btn = findViewById(R.id.download_btn);

        /*init views*/
        invoice_tv = findViewById(R.id.invoice_tv);
        date_tv = findViewById(R.id.date_tv);
        customer_name_tv = findViewById(R.id.customer_name_tv);
        customer_email_tv = findViewById(R.id.customer_email_tv);
        customer_mobile_tv = findViewById(R.id.customer_mobile_tv);
        transaction_id_tv = findViewById(R.id.transaction_id_tv);
        product_code_tv = findViewById(R.id.product_code_tv);
        product_price_tv = findViewById(R.id.product_price_tv);
        total_price_tv = findViewById(R.id.total_price_tv);

        /*get data from Bill History Adapter*/
        invoice = getIntent().getStringExtra("invoice");
        date = getIntent().getStringExtra("date");
        customer_email = getIntent().getStringExtra("email");
        customer_name = getIntent().getStringExtra("name");
        customer_mobile = getIntent().getStringExtra("number");
        transaction = getIntent().getStringExtra("transaction");
        product_code = getIntent().getStringExtra("pcode");
        product_price = getIntent().getStringExtra("price");


        /*set data on views*/
        invoice_tv.setText(invoice);
        date_tv.setText(date);
        customer_name_tv.setText(customer_name);
        customer_email_tv.setText(customer_email);
        customer_mobile_tv.setText(customer_mobile);
        transaction_id_tv.setText(transaction);
        product_code_tv.setText(product_code);
        product_price_tv.setText(product_price + " ৳");
        total_price_tv.setText(product_price + " ৳");

        askPermissions();

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make_pdf();
            }
        });
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    /* ##################################################################### */
    /*qr pdf file*/
    /* ##################################################################### */
    private void make_pdf() {

        DisplayMetrics displayMetrics = new DisplayMetrics();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getDisplay().getRealMetrics(displayMetrics);
        } else
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        infoLl.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));

        Log.d("my log", "Width Now " + infoLl.getMeasuredWidth());

        // cardView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        // Create a new PdfDocument instance
        PdfDocument document = new PdfDocument();

        // Obtain the width and height of the view
        int viewWidth = infoLl.getMeasuredWidth();
        int viewHeight = infoLl.getMeasuredHeight();


        // Create a PageInfo object specifying the page attributes
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();

        // Start a new page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the Canvas object to draw on the page
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling the view
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // Draw the view on the canvas
        infoLl.draw(canvas);

        // Finish the page
        document.finishPage(page);

        // Specify the path and filename of the output PDF file
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        /*time wise print*/
        /*save pdf file on timestamp wise*/
        long timestamps = System.currentTimeMillis() / 1000;
        String fileName = "TroVato_"+timestamps + ".pdf";

        File filePath = new File(downloadsDir, fileName);

        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            document.writeTo(fos);
            document.close();
            fos.close();
            // PDF conversion successful
            Toast.makeText(this, "Bill download successful", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Bill download un-successful", Toast.LENGTH_SHORT).show();
        }

    }

}