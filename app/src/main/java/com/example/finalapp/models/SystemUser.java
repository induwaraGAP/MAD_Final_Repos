package com.example.finalapp.models;

public class SystemUser {
    private static   String name;
    private static  String id;
    private static  String group;
    private static  Integer year;
    private static  Integer sem;
    private static String faculty;
    private static  String type;


    public SystemUser(String name,String id,String group,Integer year,Integer sem,String faculty,String type ) {
        this.name = name;
        this.id =  id;
        this.group = group;
        this.year = year;
        this.sem =  sem;
        this.faculty = faculty;
        this.type = type;
    }

    public SystemUser() {
    }

    public static void setYear(Integer year) {
        SystemUser.year = year;
    }

    public static Integer getSem() {
        return sem;
    }

    public static void setSem(Integer sem) {
        SystemUser.sem = sem;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        SystemUser.type = type;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SystemUser.name = name;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        SystemUser.id = id;
    }

    public static String getGroup() {
        return group;
    }

    public static void setGroup(String group) {
        SystemUser.group = group;
    }

    public static int getYear() {
        return year;
    }

    public static void setYear(int year) {
        SystemUser.year = year;
    }

    public static String getFaculty() {
        return faculty;
    }

    public static void setFaculty(String faculty) {
        SystemUser.faculty = faculty;
    }
}
