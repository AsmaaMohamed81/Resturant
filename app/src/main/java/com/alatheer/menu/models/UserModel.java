package com.alatheer.menu.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elashry on 10/10/2018.
 */

public class UserModel implements Serializable {
   private String user_name;
    private String user_pass;
    private String user_email;
    private String user_phone;
    private String country_id_fk;
    private int success;
    private String message;
    private String role_id_fk;
    private  String user_id;
    private String approved;
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getCountry_id_fk() {
        return country_id_fk;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getRole_id_fk() {
        return role_id_fk;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getApproved() {
        return approved;
    }
}
