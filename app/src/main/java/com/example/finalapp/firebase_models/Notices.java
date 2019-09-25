package com.example.finalapp.firebase_models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Notices {
    private String msg_id;
    private  String sender;
    private ArrayList<String> recievers  =  new ArrayList<String>();
    private String message;
    private String date;
    private String subject;

    public Notices() {
    }

    public Notices(String sender, ArrayList<String> recievers,String message,String subject) {
        this.sender = sender;
        this.recievers = recievers;
        this.message =  message;
        this.date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());;
        this.subject = subject;
        this.msg_id =  this.sender+"sep" + this.date ;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ArrayList<String> getRecievers() {
        return recievers;
    }

    public void setRecievers(ArrayList<String> recievers) {
        this.recievers = recievers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
