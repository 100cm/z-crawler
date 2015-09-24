package com.zhou.entity;

/**
 * Created by icepoint on 9/19/15.
 */
public class Papers {



    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Papers(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Papers() {
        super();
    }
}
