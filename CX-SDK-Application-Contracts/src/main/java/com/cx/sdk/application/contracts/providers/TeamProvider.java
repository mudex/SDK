package com.cx.sdk.application.contracts.providers;

import com.cx.sdk.domain.Session;
import com.cx.sdk.domain.entities.Team;
import com.cx.sdk.domain.exceptions.SdkException;

import java.util.List;

/**
 * Created by victork on 22/03/2017.
 */
public interface TeamProvider {
    List<Team> getTeams(Session session) throws RuntimeException;
}
