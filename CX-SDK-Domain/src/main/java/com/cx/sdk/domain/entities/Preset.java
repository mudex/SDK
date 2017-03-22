package com.cx.sdk.domain.entities;

/**
 * Created by victork on 22/03/2017.
 */
public class Preset {
    private String id;
    private String name;

    public Preset(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Preset setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Preset setName(String name) {
        this.name = name;
        return this;
    }
}
