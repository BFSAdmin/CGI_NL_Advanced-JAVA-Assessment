package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.model.request.CountryRequest;
import com.nl.cgi.bff.model.response.CountryResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class CountryFrontService {
    private final PersistenceServiceClient persistenceServiceClient;
    private final PersistenceServiceProperties persistenceServiceProperties;

    public CountryResponse getCountryDetails() {
        return persistenceServiceClient.getCountryDetails(persistenceServiceProperties.getCountryDetails());
    }

    public boolean saveCountryDetails(CountryRequest countryRequest) {
        return persistenceServiceClient.saveCountryDetails(countryRequest ,persistenceServiceProperties.getCountryDetails());
    }
}
