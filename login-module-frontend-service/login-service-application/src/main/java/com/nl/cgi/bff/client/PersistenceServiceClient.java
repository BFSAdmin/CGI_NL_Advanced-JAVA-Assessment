package com.nl.cgi.bff.client;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.model.request.AuthorisationRequest;
import com.nl.cgi.bff.model.request.CountryRequest;
import com.nl.cgi.bff.model.request.LoginRequest;
import com.nl.cgi.bff.model.response.CountryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersistenceServiceClient {

    private final WebClient persistenceWebClient;

    /**
     *
     * @param
     * @param requestUrl
     * @return
     */
    public boolean getUserDetails(LoginRequest loginRequest, String requestUrl) {
        return persistenceWebClient
                .post()
               .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get user details call")))
                .bodyToMono(Boolean.class)
                .flatMap(authorisationResponse -> ExceptionUtil.validateGetResponse(authorisationResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    public boolean saveUserDetails(AuthorisationRequest authorisationRequest, String requestUrl ) {
        return persistenceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(authorisationRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save user details call")))
                .bodyToMono(Boolean.class)
                .flatMap(authorisationResponse -> ExceptionUtil.validateSaveResponse(authorisationResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     *
     * @param requestUrl
     * @return
     */
    public CountryResponse getCountryDetails(String requestUrl ) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get country details call")))
                .bodyToMono(CountryResponse.class)
                .flatMap(countryResponse -> ExceptionUtil.validateGetCountryResponse(countryResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     *
     * @param countryRequest
     * @param requestUrl
     * @return
     */
    public boolean saveCountryDetails(CountryRequest countryRequest, String requestUrl ) {
        return persistenceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(countryRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save country details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }




}
