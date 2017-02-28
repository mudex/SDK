package com.cx.sdk.api;

import com.cx.sdk.application.contracts.providers.LoginProvider;
import com.cx.sdk.application.services.LoginService;
import com.cx.sdk.application.services.LoginServiceImpl;
import com.cx.sdk.domain.CredentialsValidator;
import com.cx.sdk.domain.validators.CredentialsValidatorImpl;
import com.cx.sdk.infrastructure.providers.LoginProviderImpl;
import com.google.inject.AbstractModule;

/**
 * Created by victork on 28/02/2017.
 */
public class Bootstrapper extends AbstractModule {


    private SdkConfiguration configuration;

    public Bootstrapper(SdkConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        registerApiDependencies();
        registerApplicationDependencies();
        registerDomainDependencies();
        registerInfrastructureDependencies();
    }

    private void registerApiDependencies() {
        bind(CxClient.class).to(CxClientImpl.class);
    }

    private void registerApplicationDependencies() {
        bind(LoginService.class).to(LoginServiceImpl.class);
    }

    private void registerDomainDependencies() {
        bind(CredentialsValidator.class).to(CredentialsValidatorImpl.class);
    }

    private void registerInfrastructureDependencies() {
        bind(LoginProvider.class).to(LoginProviderImpl.class);
    }
}
