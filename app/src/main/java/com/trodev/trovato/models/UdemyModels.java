package com.trodev.trovato.models;

public class UdemyModels {

    String cname, clanguage, crating, clink, cdescriptio, image,date, time;

    public UdemyModels() {
    }

    public UdemyModels(String cname, String clanguage, String crating, String clink, String cdescriptio, String image) {
        this.cname = cname;
        this.clanguage = clanguage;
        this.crating = crating;
        this.clink = clink;
        this.cdescriptio = cdescriptio;
        this.image = image;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getClanguage() {
        return clanguage;
    }

    public void setClanguage(String clanguage) {
        this.clanguage = clanguage;
    }

    public String getCrating() {
        return crating;
    }

    public void setCrating(String crating) {
        this.crating = crating;
    }

    public String getClink() {
        return clink;
    }

    public void setClink(String clink) {
        this.clink = clink;
    }

    public String getCdescriptio() {
        return cdescriptio;
    }

    public void setCdescriptio(String cdescriptio) {
        this.cdescriptio = cdescriptio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
