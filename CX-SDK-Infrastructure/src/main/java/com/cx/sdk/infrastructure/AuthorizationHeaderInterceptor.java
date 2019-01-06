package com.cx.sdk.infrastructure;

import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AuthorizationHeaderInterceptor extends AbstractPhaseInterceptor<Message> {

    private String token;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER = "Bearer ";

    public AuthorizationHeaderInterceptor(String token) {
        super(Phase.WRITE);
        this.token = token;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));
        headers.put(AUTHORIZATION_HEADER, Collections.singletonList(BEARER + token));
    }

}