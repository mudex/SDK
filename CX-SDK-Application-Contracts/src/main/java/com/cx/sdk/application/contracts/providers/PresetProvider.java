package com.cx.sdk.application.contracts.providers;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.exceptions.SdkException;

import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public interface PresetProvider {
    List<Preset> getPresets(Session session) throws SdkException;
}
