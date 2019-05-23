package com.oosad.cddgame.Data;

import java.io.Serializable;

public class User implements Serializable {

    public User(String name) {
        this.name = name;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
