package com.cx.sdk.infrastructure.providers;

import com.checkmarx.v7.CxWSResponsePresetList;
import com.cx.sdk.application.contracts.providers.PresetProvider;
import com.cx.sdk.application.contracts.providers.SDKConfigurationProvider;
import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Preset;
import com.cx.sdk.domain.exceptions.SdkException;
import com.cx.sdk.infrastructure.CxSoapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public class PresetProviderImpl implements PresetProvider {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CxSoapClient cxSoapClient;
    private final SDKConfigurationProvider sdkConfigurationProvider;

    public PresetProviderImpl(SDKConfigurationProvider sdkConfigurationProvider) {
        this.sdkConfigurationProvider = sdkConfigurationProvider;
        this.cxSoapClient = new CxSoapClient(this.sdkConfigurationProvider);
    }

    @Override
    public List<Preset> getPresets(Session session) throws Exception {
        List<Preset> presets = new ArrayList<>();
        try {
            CxWSResponsePresetList response = this.cxSoapClient.getPresets(session);
            for (com.checkmarx.v7.Preset preset : response.getPresetList().getPreset()) {
                presets.add(new Preset(Long.toString(preset.getID()), preset.getPresetName()));
            }
        } catch (Exception e) {
            logger.error("[SDK][PresetProviderImpl] Failed to get presets", e);
            throw e;
        }
        return presets;
    }
}
