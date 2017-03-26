package com.cx.sdk.api.dtos;

/**
 * Created by victork on 26/03/2017.
 */
public class EngineConfigurationDTO {
    private String id;
    private String name;

    public EngineConfigurationDTO() {}

    public EngineConfigurationDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public EngineConfigurationDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EngineConfigurationDTO setName(String name) {
        this.name = name;
        return this;
    }
}
