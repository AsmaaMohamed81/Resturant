package com.alatheer.menu.models;

import java.io.Serializable;

/**
 * Created by elashry on 29/08/2018.
 */

public class Filter_Model implements Serializable {
    String name;

    public Filter_Model(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
