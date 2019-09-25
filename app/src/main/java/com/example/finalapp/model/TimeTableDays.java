package com.example.finalapp.model;

import java.util.List;

public class TimeTableDays {
    private String combine_id;
    private String sub_id;
    private String class_type;
    private String group;
    private String faculty;
    private Integer year;
    private Integer sem;
    private String day;
    private String starttime;
    private String endtime;
    private String hallno;
    private List<String> lecturer;


    public TimeTableDays() {
    }

    /*public TimeTableDays(String combine_id, String sub_id, String group, String faculty, Integer year, Integer sem, String starttime, String endtime, String hallno, List<String> Lecturer) {
        this.combine_id = combine_id;
        this.sub_id = sub_id;
        this.group = group;
        this.faculty = faculty;
        this.year = year;
        this.sem = sem;
        this.starttime = starttime;
        this.endtime = endtime;
        this.hallno = hallno;
        this.lecturer = Lecturer;
    }*/

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getLecturer() {
        return lecturer;
    }

    public void setLecturer(List<String> lecturer) {
        this.lecturer = lecturer;
    }

    public String getHallno() {
        return hallno;
    }

    public void setHallno(String hallno) {
        this.hallno = hallno;
    }

    public String getCombine_id() {
        return combine_id;
    }

    public void setCombine_id(String combine_id) {
        this.combine_id = combine_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
