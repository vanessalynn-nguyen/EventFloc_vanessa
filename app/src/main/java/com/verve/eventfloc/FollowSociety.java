package com.verve.eventfloc;

/**
 * Created by Vanessa on 8/05/2015.
 */
public class FollowSociety {
    private int societyID;
    private int studentID;

    public FollowSociety(int societyID, int studentID) {
        this.societyID = societyID;
        this.studentID = studentID;
    }

    public int getSocietyID() {
        return societyID;
    }

    public void setSocietyID(int societyID) {
        this.societyID = societyID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
}

