package com.trodev.trovato;

public class BillModels {

    String aid, name, mobile, email, price,  date, time, year, bill_no,  uid;

    public BillModels() {
    }

    public BillModels(String aid, String name, String mobile, String email, String price, String date, String time, String year, String bill_no, String uid) {
        this.aid = aid;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.price = price;
        this.date = date;
        this.time = time;
        this.year = year;
        this.bill_no = bill_no;
        this.uid = uid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
