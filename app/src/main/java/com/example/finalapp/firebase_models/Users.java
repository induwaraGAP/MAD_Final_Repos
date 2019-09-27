package com.example.finalapp.firebase_models;

public class Users {
    private String IT_NO;
    private String name;
    private Integer year;
    private Integer sem;
    private String group;
    private String faculty;
    private String password;

    private String type; //to check whether a student or a lecturer



    public Users(){

    }

    //IT_NO, Name, Year, Semester, Group, Faculty, Password, Type
    public Users(String IT_NO, String name, Integer year, Integer sem, String group, String faculty,String password,String type) {
        this.IT_NO = IT_NO;
        this.name = name;
        this.year = year;
        this.sem = sem;
        this.group = group;
        this.faculty = faculty;
        this.password = password;

        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getIT_NO() {
        return IT_NO;
    }

    public void setIT_NO(String IT_NO) {
        this.IT_NO = IT_NO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
