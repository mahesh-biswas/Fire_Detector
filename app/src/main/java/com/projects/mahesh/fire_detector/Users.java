package com.projects.mahesh.fire_detector;

public class Users {
    String uid;
    String name;
    String username;
    String password;
    String society;
    String flatId;

    /*
        pi will be feeded with customer's contact info. Then,
        pi when connected with Firebase Database;
        a unique key will be generated named as flatID.
        this flatID will be mailed to the user, withn the info provided at initial stage.
        this flatID is must for signup.
     */
    public Users() {
    }

    public Users(String uid, String name, String username, String password,String society, String flatId) {
        this.uid = uid;
        this.name = name;
        this.username = username;
        this.password = password;
        this.flatId = flatId;
        this.society = society;
    }

    public String getSociety() {
        return society;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFlatId() {
        return flatId;
    }
}
