package com.cx.sdk.api.dtos;

/**
 * Created by victork on 26/03/2017.
 */
public class PresetDTO {
    private String id;
    private String name;

    public PresetDTO() {}

    public PresetDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public PresetDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PresetDTO setName(String name) {
        this.name = name;
        return this;
    }
}
