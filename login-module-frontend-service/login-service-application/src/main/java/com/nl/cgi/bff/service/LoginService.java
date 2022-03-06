package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.model.request.AuthorisationRequest;
import com.nl.cgi.bff.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class LoginService {

    private final PersistenceServiceClient persistenceServiceClient;
    private final PersistenceServiceProperties persistenceServiceProperties;

    /**
     *
     * @param loginRequest
     * @return
     */
    public boolean isValidUserDetails(LoginRequest loginRequest) {
        log.debug("login service method {}", loginRequest.getUserName());
        return persistenceServiceClient.getUserDetails(loginRequest, persistenceServiceProperties.getUserAuthenticationUrl());
    }

    /**
     *
     * @param authorisationRequest
     * @return
     */
    public boolean saveUserDetails(AuthorisationRequest authorisationRequest) {
        log.debug("login service method saveUserDetails{}", authorisationRequest.getUserName());
        return persistenceServiceClient.saveUserDetails(authorisationRequest, persistenceServiceProperties.getSaveUserDetails());
    }

}
