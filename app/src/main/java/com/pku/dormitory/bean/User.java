package com.pku.dormitory.bean;

import java.io.Serializable;

/**
 * Created by Tammy on 2017/12/12.
 */

public class User implements Serializable{
    private String studentid;
    private String name;
    private String gender;
    private String vcode;
    private String room;
    private String building;
    private String location;
    private String grade;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public User(String studentid, String name, String gender, String vcode, String room, String building, String location, String grade) {
        this.studentid = studentid;
        this.name = name;
        this.gender = gender;
        this.vcode = vcode;
        this.room = room;
        this.building = building;
        this.location = location;
        this.grade = grade;
    }
}



