package com.oosad.cddgame.Data;

import java.io.Serializable;

public class Robot extends Player implements Serializable {

    public Robot(int id) {
        this.Id = id;
    }

    private int Id;

    public int getId() {
        return this.Id;
    }

    public boolean showCard() {

        return true;
    }
}
