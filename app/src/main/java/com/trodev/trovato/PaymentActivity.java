package com.trodev.trovato;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.help5g.uddoktapaysdk.UddoktaPay;
import com.trodev.trovato.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static final String API_KEY = "4be324433504126ddd49662efcf7f111740895b6";
    private static final String CHECKOUT_URL = "https://payment.trodev.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://payment.trodev.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://www.trodev.com";
    private static final String CANCEL_URL = "https://www.trodev.com";

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
    TextInputEditText nameEt, emailEt, amountEt, user_token_Et, packagesET, mobileEt;
    LinearLayout webLayout, uiLayout;
    WebView payWebView;
    TextView resultTv;
    DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    BillModels userProfile;
    AutoCompleteTextView autoCompleteTextView;
    String b_name, b_email, b_price, b_mobile, b_month, b_packages, b_user_id;
    String biller_id;
    WebView webview;
    String name, number, user_token, price;
    DatabaseReference databaseReference;
    MaterialButton payBtn;

    String u_name, u_email, u_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        /*init views*/
        webview = findViewById(R.id.payWebView);
        payBtn = findViewById(R.id.payBtn);

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


        /*####################################################################################################################################*/
        /*####################################################################################################################################*/
        /*get admin info from admin user database*/
        /*database location and get user uid*/
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        /*show user profile data*/
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {

                     u_name = userProfile.uname;
                     u_email = userProfile.email;
                     u_mobile = userProfile.num;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

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

        uiLayout.setVisibility(View.VISIBLE);

        bill_payment();

        /*payment gateway task opening*/
        // go_to_payment();

    }

    private void go_to_payment() {

        uiLayout.setVisibility(View.GONE);
        webLayout.setVisibility(View.VISIBLE);

        String fullname = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String amount = amountEt.getText().toString().trim();

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
                storedFullName = nameEt.getText().toString().trim();
                storedEmail = emailEt.getText().toString().trim();
                storedAmount = amountEt.getText().toString().trim();
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
                            // uiLayout.setVisibility(View.VISIBLE);
                            // webLayout.setVisibility(View.GONE);
                            resultTv.setText("Payment Complete" + "\n" + "Name:  " + storedFullName + "\n" + "Amount: " + storedAmount);


                        } else if ("PENDING".equals(status)) {
                            // Handle payment pending case
                            // Handle payment completed case
                            uiLayout.setVisibility(View.VISIBLE);
                            webLayout.setVisibility(View.GONE);
                            resultTv.setText("Payment Pending" + "\n" + "Name:  " + storedFullName + "\n" + "Amount: " + storedAmount);
                            payment_pending();

                        } else if ("ERROR".equals(status)) {
                            // Handle payment error case
                            // Handle payment completed case
                            uiLayout.setVisibility(View.VISIBLE);
                            webLayout.setVisibility(View.GONE);
                            resultTv.setText("Payment Error" + "\n" + "Name:  " + storedFullName + "\n" + "Amount: " + storedAmount);
                            error_sms();
                        }
                    }
                });
            }
        };

        UddoktaPay uddoktapay = new UddoktaPay(payWebView, paymentCallback);
        uddoktapay.loadPaymentForm(API_KEY, fullname, email, amount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);

    }

    private void payment_pending(){
        Toast.makeText(this, "Payment loading", Toast.LENGTH_SHORT).show();
    }

    private void error_sms(){
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }


    private void bill_payment() {

        /*when click button then show animation and toast*/
        Toast.makeText(PaymentActivity.this, "Bill Payment Processing", Toast.LENGTH_SHORT).show();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("user_payment");

        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        // user_token = getIntent().getStringExtra("uid");
        price = getIntent().getStringExtra("u_price");


        // if (user_token.isEmpty()) {

        // } else {


            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            String date = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            String time = currentTime.format(calForTime.getTime());

            Calendar calForYear = Calendar.getInstance();
            SimpleDateFormat currentYear = new SimpleDateFormat("yyyy");
            String year = currentYear.format(calForYear.getTime());

            /*bill no*/
            long timeStamp = System.currentTimeMillis()/1000;
            String currentTimeStamp = Long.toString(timeStamp);

            String key = databaseReference.push().getKey();


            if (key != null) {
                /*set data on user_status*/
                BillModels billModels = new BillModels(key, u_name, u_email, u_email, price, date, time, year, currentTimeStamp,  FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.child(key).setValue(billModels);
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        // }

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