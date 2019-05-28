package com.oosad.cddgame.Data.Entity.Player;

import java.io.Serializable;

public class User extends Player implements Serializable {

    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean IsRobot() {
        return false;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
