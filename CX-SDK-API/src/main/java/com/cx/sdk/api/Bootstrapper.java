package com.cx.sdk.api;

import com.cx.sdk.application.login.services.LoginService;
import com.cx.sdk.application.login.services.LoginServiceImp;
import com.cx.sdk.providers.CredentialsValidator;
import com.cx.sdk.providers.CredentialsValidatorImp;
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
        bind(CxClient.class).to(CxClientImp.class);
    }

    private void registerApplicationDependencies() {
        bind(LoginService.class).to(LoginServiceImp.class);
    }

    private void registerDomainDependencies() {
        bind(CredentialsValidator.class).to(CredentialsValidatorImp.class);
    }

    private void registerInfrastructureDependencies() {
        //TODO: register loginProvider
    }
}
