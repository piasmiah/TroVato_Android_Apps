package com.trodev.trovato.activity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.help5g.uddoktapaysdk.UddoktaPay;
import com.trodev.trovato.models.BillModels;
import com.trodev.trovato.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static final String API_KEY = "5078f2cffe40017a8e7648b5ec98c763035f44b8";
    private static final String CHECKOUT_URL = "https://payment.trodev.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://payment.trodev.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://www.trodev.com";
    private static final String CANCEL_URL = "https://www.trodev.com/error";

    // Instance variables to store payment information
    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;
    LinearLayout webLayout, uiLayout;
    WebView payWebView;
    TextView resultTv;
    DatabaseReference reference;
    private FirebaseUser user;
    private String userID;

    String biller_id;
    WebView webview;
    DatabaseReference databaseReference;
    MaterialButton payBtn;
    ImageView back_btn, status_img;
    String user_name, user_mobile, user_price, user_pcode, user_email, user_bill_no, product_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        /*support action bar hide*/
        getSupportActionBar().hide();

        /*init views*/
        webview = findViewById(R.id.payWebView);
        payBtn = findViewById(R.id.payBtn);
        back_btn = findViewById(R.id.back_btn);
        status_img = findViewById(R.id.status_img);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // ###########################################################
        // WebSite Address Here
        /*web view & ui layout*/
        webLayout = findViewById(R.id.webLayout);
        uiLayout = findViewById(R.id.uiLayout);
        payWebView = findViewById(R.id.payWebView);

        //############################################################

        // if you set this size in your website, Fixed it or don't use this
        webview.setInitialScale(90);

        //#############################################################
        // Website Zoom control
        // web view.getSettings().setBuiltInZoomControls(true);

        long timestamps = System.currentTimeMillis() / 1000;
        biller_id = String.valueOf(timestamps);


        WebSettings mywebsetting = webview.getSettings();

        mywebsetting.setJavaScriptEnabled(true);

        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setDatabaseEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebsetting.setDomStorageEnabled(true);
        mywebsetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mywebsetting.setUseWideViewPort(true);
        mywebsetting.setSavePassword(true);
        mywebsetting.setSaveFormData(true);
        mywebsetting.setEnableSmoothTransition(true);


        // ############################## Download Code is Here ####################################
        // Download any File in this website.
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //Checking runtime permission for devices above Marshmallow.
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PaymentActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    downloadDialog(url, userAgent, contentDisposition, mimetype);
                } else {
                    Toast.makeText(PaymentActivity.this, "Permission granter", Toast.LENGTH_SHORT).show();
                    //requesting permissions.
                    ActivityCompat.requestPermissions(PaymentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });

        /*get data from CheckoutActivity*/
        /*this is the main data getting section*/
        user_name = getIntent().getStringExtra("c_name");
        user_mobile = getIntent().getStringExtra("c_mobile");
        user_price = getIntent().getStringExtra("c_price");
        user_pcode = getIntent().getStringExtra("c_pcode");
        user_email = getIntent().getStringExtra("c_email");
        product_name = getIntent().getStringExtra("c_pname");

        /*bill no*/
        long timeStamp = System.currentTimeMillis() / 1000;
        user_bill_no = Long.toString(timeStamp);

        //bill_payment();

        /*payment gateway task opening*/
        go_to_payment();

    }

    private void go_to_payment() {

        uiLayout.setVisibility(View.GONE);
        webLayout.setVisibility(View.VISIBLE);

        // Set your metadata values in the map
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("CustomMetaData1", "Meta Value 1");
        metadataMap.put("CustomMetaData2", "Meta Value 2");
        metadataMap.put("CustomMetaData3", "Meta Value 3");

        UddoktaPay.PaymentCallback paymentCallback = new UddoktaPay.PaymentCallback() {
            @Override
            public void onPaymentStatus(String status, String fullName, String email, String amount, String invoiceId,
                                        String paymentMethod, String senderNumber, String transactionId,
                                        String date, Map<String, String> metadataValues, String fee, String chargeAmount) {

                // Callback method triggered when the payment status is received from the payment gateway.
                // It provides information about the payment transaction.
                storedFullName = fullName;
                storedEmail = email;
                storedAmount = amount;
                storedInvoiceId = invoiceId;
                storedPaymentMethod = paymentMethod;
                storedSenderNumber = senderNumber;
                storedTransactionId = transactionId;
                storedDate = date;
                storedFee = fee;
                storedChargedAmount = chargeAmount;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Clear previous metadata values to avoid duplication
                        storedMetaKey1 = null;
                        storedMetaValue1 = null;
                        storedMetaKey2 = null;
                        storedMetaValue2 = null;
                        storedMetaKey3 = null;
                        storedMetaValue3 = null;

                        // Iterate through the metadata map and store the key-value pairs
                        for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                            String metadataKey = entry.getKey();
                            String metadataValue = entry.getValue();

                            if ("CustomMetaData1".equals(metadataKey)) {
                                storedMetaKey1 = metadataKey;
                                storedMetaValue1 = metadataValue;
                            } else if ("CustomMetaData2".equals(metadataKey)) {
                                storedMetaKey2 = metadataKey;
                                storedMetaValue2 = metadataValue;
                            } else if ("CustomMetaData3".equals(metadataKey)) {
                                storedMetaKey3 = metadataKey;
                                storedMetaValue3 = metadataValue;
                            }
                        }

                        // Update UI based on payment status
                        if ("COMPLETED".equals(status)) {
                            // Handle payment completed case
                            uiLayout.setVisibility(View.VISIBLE);
                            webLayout.setVisibility(View.GONE);
                            /*save user data our*/
                            bill_payment();

                        } else if ("PENDING".equals(status)) {
                            // Handle payment pending case
                            uiLayout.setVisibility(View.VISIBLE);
                            webLayout.setVisibility(View.GONE);

                        } else if ("ERROR".equals(status)) {
                            // Handle payment error case
                            uiLayout.setVisibility(View.VISIBLE);
                            webLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };

        UddoktaPay uddoktapay = new UddoktaPay(payWebView, paymentCallback);
        uddoktapay.loadPaymentForm(API_KEY, user_name, user_email, user_price, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);

    }

    private void bill_payment() {

        /*when click button then show animation and toast*/
        Toast.makeText(PaymentActivity.this, "Bill Payment Processing", Toast.LENGTH_SHORT).show();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("user_payment");

        if (user_price.isEmpty()) {

        } else {

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            String date = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            String time = currentTime.format(calForTime.getTime());

            Calendar calForYear = Calendar.getInstance();
            SimpleDateFormat currentYear = new SimpleDateFormat("yyyy");
            String year = currentYear.format(calForYear.getTime());

            String key = databaseReference.push().getKey();

            if (key != null) {
                /*set data on user_status*/
                BillModels billModels = new BillModels(key, user_name, user_mobile, user_email, user_price, date, time, year, biller_id, user_pcode, storedTransactionId, product_name, FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.child(key).setValue(billModels);
                Toast.makeText(this, "Invoice Saved Successful", Toast.LENGTH_SHORT).show();
                status_img.setImageResource(R.drawable.successfull_img);

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void downloadDialog(final String url, final String userAgent, String contentDisposition, String mimetype) {

        //getting filename from url.
        final String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);

        //alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);

        //title of alertdialog
        builder.setTitle("Download File");

        //message of alertdialog
        builder.setMessage("Do you want to download " + filename);

        //if Yes button clicks.
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //DownloadManager.Request created with url.
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                //cookie
                String cookie = CookieManager.getInstance().getCookie(url);

                //Add cookie and User-Agent to request
                request.addRequestHeader("Cookie", cookie);
                request.addRequestHeader("User-Agent", userAgent);

                //file scanned by Media Scannar
                request.allowScanningByMediaScanner();

                //Download is visible and its progress, after completion too.
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                //DownloadManager created
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                //Saving files in Download folder
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

                //download enqued
                downloadManager.enqueue(request);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //cancel the dialog if Cancel clicks
                dialog.cancel();
            }
        });

        //alertdialog shows.
        builder.create().show();
    }

    // One-BackPress Method is here....
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {

            webview.goBack();
            webview.clearCache(true);

        } else {

            super.onBackPressed();

        }
    }

}