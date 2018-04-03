package com.xorek.measurementbook;

/**
 * Created by MEA on 8/20/2017.
 */

public class Contact {
    String na,phone,date,deliver;

    public Contact() {
    }

    public Contact(String na) {
        this.na = na;
    }

    public Contact(String na, String phone, String date, String deliver) {
        this.na = na;
        this.phone = phone;
        this.date = date;
        this.deliver = deliver;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }
}
