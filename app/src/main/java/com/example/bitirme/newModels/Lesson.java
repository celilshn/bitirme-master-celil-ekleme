package com.example.bitirme.newModels;

import java.util.ArrayList;

public class Lesson {
    private int credit;
    private String QrVal;
    private String lessonName;
    private String lessonCode;
    private boolean activity;
    private ArrayList<String> checkedUsers;

    public ArrayList<String> getCheckedUsers() {
        return checkedUsers;
    }

    public void setCheckedUsers(ArrayList<String> checkedUsers) {
        this.checkedUsers = checkedUsers;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public Lesson() {
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getQrVal() {
        return QrVal;
    }

    public void setQrVal(String qrVal) {
        QrVal = qrVal;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonCode() {
        return lessonCode;
    }

    public void setLessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }
}
