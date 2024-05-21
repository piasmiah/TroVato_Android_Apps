package com.trodev.trovato.models;

public class EnvatoModels {

    String pcode, pname, pprice, pvideo, plicense, pdes, fileSize_ET, appSup_ET, date, time, zip_link, zip_password, image;

    public EnvatoModels() {
    }

    public EnvatoModels(String pcode, String pname, String pprice, String pvideo, String plicense, String pdes, String fileSize_ET, String appSup_ET, String date, String time, String zip_link, String zip_password, String image) {
        this.pcode = pcode;
        this.pname = pname;
        this.pprice = pprice;
        this.pvideo = pvideo;
        this.plicense = plicense;
        this.pdes = pdes;
        this.fileSize_ET = fileSize_ET;
        this.appSup_ET = appSup_ET;
        this.date = date;
        this.time = time;
        this.zip_link = zip_link;
        this.zip_password = zip_password;
        this.image = image;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPvideo() {
        return pvideo;
    }

    public void setPvideo(String pvideo) {
        this.pvideo = pvideo;
    }

    public String getPlicense() {
        return plicense;
    }

    public void setPlicense(String plicense) {
        this.plicense = plicense;
    }

    public String getPdes() {
        return pdes;
    }

    public void setPdes(String pdes) {
        this.pdes = pdes;
    }

    public String getFileSize_ET() {
        return fileSize_ET;
    }

    public void setFileSize_ET(String fileSize_ET) {
        this.fileSize_ET = fileSize_ET;
    }

    public String getAppSup_ET() {
        return appSup_ET;
    }

    public void setAppSup_ET(String appSup_ET) {
        this.appSup_ET = appSup_ET;
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

    public String getZip_link() {
        return zip_link;
    }

    public void setZip_link(String zip_link) {
        this.zip_link = zip_link;
    }

    public String getZip_password() {
        return zip_password;
    }

    public void setZip_password(String zip_password) {
        this.zip_password = zip_password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
