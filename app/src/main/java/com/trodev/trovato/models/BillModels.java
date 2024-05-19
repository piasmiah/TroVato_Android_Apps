package com.trodev.trovato.models;

public class BillModels {

    String aid, user_name, user_mobile, user_email, product_price, payment_date, payment_time, payment_year, payment_bill_no, product_code, transactionId, uid;

    public BillModels() {
    }

    public BillModels(String aid, String user_name, String user_mobile, String user_email, String product_price, String payment_date, String payment_time, String payment_year, String payment_bill_no, String product_code, String transactionId, String uid) {
        this.aid = aid;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.user_email = user_email;
        this.product_price = product_price;
        this.payment_date = payment_date;
        this.payment_time = payment_time;
        this.payment_year = payment_year;
        this.payment_bill_no = payment_bill_no;
        this.product_code = product_code;
        this.transactionId = transactionId;
        this.uid = uid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getPayment_year() {
        return payment_year;
    }

    public void setPayment_year(String payment_year) {
        this.payment_year = payment_year;
    }

    public String getPayment_bill_no() {
        return payment_bill_no;
    }

    public void setPayment_bill_no(String payment_bill_no) {
        this.payment_bill_no = payment_bill_no;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
