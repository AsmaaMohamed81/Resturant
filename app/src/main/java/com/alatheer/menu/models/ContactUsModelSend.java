package com.alatheer.menu.models;

import java.io.Serializable;

/**
 * Created by elashry on 02/09/2018.
 */

public class ContactUsModelSend implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String message;


    public ContactUsModelSend(String name, String email, String phone, String message) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
