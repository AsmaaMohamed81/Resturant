package com.alatheer.menu.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class productitemModel implements Serializable {

    private String unit_id_fk;
    private List<String> IngIDlist;
    private String name;
    private String size_name;
    private String size_id;
    private String image;
    private String price;
    private ArrayList<String> Ingnameist;
    private String count;
    String id;


    public productitemModel(String id, String unit_id_fk, List<String> ingIDlist, String name, String size_namee,String size_id, String image, String price, ArrayList<String> ingnameist, String count) {
        this.unit_id_fk = unit_id_fk;
        IngIDlist = ingIDlist;
        this.name = name;
        this.size_name = size_namee;
        this.size_id = size_id;
        this.image = image;
        this.price = price;
        Ingnameist = ingnameist;
        this.count = count;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit_id_fk() {
        return unit_id_fk;
    }

    public void setUnit_id_fk(String unit_id_fk) {
        this.unit_id_fk = unit_id_fk;
    }

    public List<String> getIngIDlist() {
        return IngIDlist;
    }

    public void setIngIDlist(List<String> ingIDlist) {
        IngIDlist = ingIDlist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }


    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getIngnameist() {
        return Ingnameist;
    }

    public void setIngnameist(ArrayList<String> ingnameist) {
        Ingnameist = ingnameist;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
