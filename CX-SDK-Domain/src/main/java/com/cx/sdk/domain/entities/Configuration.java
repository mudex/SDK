package com.cx.sdk.domain.entities;

/**
 * Created by victork on 22/03/2017.
 */
public class Configuration {
    private String id;
    private String name;

    public Configuration(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Configuration setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Configuration setName(String name) {
        this.name = name;
        return this;
    }
}
