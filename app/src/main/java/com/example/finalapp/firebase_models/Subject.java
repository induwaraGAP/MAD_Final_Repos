package com.example.finalapp.firebase_models;

import java.util.ArrayList;

public class Subject {
    private String sub_id;
    private String sub_name;
    private String faculty;
    private  String users;

    public Subject() {
    }

    public Subject(String sub_id, String sub_name, String faculty, String users) {
        this.sub_id = sub_id;
        this.sub_name = sub_name;
        this.faculty = faculty;
        this.users = users;

    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getSub_id() {
        return sub_id;
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


}