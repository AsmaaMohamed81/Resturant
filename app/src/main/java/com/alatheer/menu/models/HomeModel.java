package com.alatheer.menu.models;

public class HomeModel {

    private int img;
    private String type;
    private String time;
    private String detail;
    private String size;


    public HomeModel(int img, String type, String time, String detail, String size) {
        this.img = img;
        this.type = type;
        this.time = time;
        this.detail = detail;
        this.size = size;
    }

    public int getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getDetail() {
        return detail;
    }

    public String getSize() {
        return size;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
