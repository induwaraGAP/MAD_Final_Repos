package com.example.finalapp.firebase_models;

import java.util.ArrayList;

public class Subject {
    private String sub_id;
    private String sub_name;
    private String faculty;
    private  Users users;
    private ArrayList<String> temp;
    public Subject() {
    }

    public Subject(String sub_id, String sub_name, String faculty, Users users,ArrayList<String> temp) {
        this.sub_id = sub_id;
        this.sub_name = sub_name;
        this.faculty = faculty;
        this.users = users;
        this.temp =  temp;
    }

    public String getSub_id() {
        return sub_id;
    }

    public ArrayList<String> getTemp() {
        return temp;
    }

    public void setTemp(ArrayList<String> temp) {
        this.temp = temp;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}