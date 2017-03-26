package com.cx.sdk.api.dtos;

/**
 * Created by victork on 26/03/2017.
 */
public class TeamDTO {
    private String id;
    private String name;

    public TeamDTO() {}

    public TeamDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public TeamDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamDTO setName(String name) {
        this.name = name;
        return this;
    }
}
