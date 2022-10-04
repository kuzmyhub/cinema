package model;

import java.io.Serializable;

public class Session implements Serializable {

    private int id;

    private String name;

    public Session(int id) {
    }

    public Session(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
