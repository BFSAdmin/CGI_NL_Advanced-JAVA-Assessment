package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.CountryRequest;
import com.nl.cgi.bff.model.response.CountryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CountryFrontServiceTest {
    CountryFrontService countryFrontService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        countryFrontService = new CountryFrontService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("Get the User Details")
    class RequestGetSaveCountryDetails {

        @Test
        void testGetCountryDetails() {
            when(persistenceServiceClient.getCountryDetails(any())).thenReturn(MockDataProvider.getCountryDetails());
            CountryResponse countryDetails = countryFrontService.getCountryDetails();
            assertNotNull(countryDetails);
            assertEquals("INDIA", countryDetails.getCountryNames().get(0).getCountryName());
            assertEquals("USA", countryDetails.getCountryNames().get(1).getCountryName());
            verify(persistenceServiceClient, times(1)).getCountryDetails(any());
        }


        @Test
        void testGetCountryWhenDetailsISNotFound() {
            when(persistenceServiceClient.getCountryDetails(any())).thenThrow(new ServiceException("Country Details is not Found"));
            ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                    countryFrontService.getCountryDetails());
            verify(persistenceServiceClient, times(1)).getCountryDetails(any());
            assertEquals("Country Details is not Found", invalidRequestException.getMessage());
        }

        @Test
        void testSaveCountryDetails() {
            when(persistenceServiceClient.saveCountryDetails(any(CountryRequest.class), any())).thenReturn(true);
            boolean isCountrySaved = countryFrontService.saveCountryDetails(MockDataProvider.getCountryServiceRequest());
            assertTrue(isCountrySaved);
            verify(persistenceServiceClient, times(1)).saveCountryDetails(any(CountryRequest.class), any());
        }

        @Test
        void testSaveCountryDetailsWhenCountryNameISNull() {
            when(persistenceServiceClient.saveCountryDetails(any(CountryRequest.class), any())).thenThrow(new ServiceException("No country returned from persistence service"));
          var request =MockDataProvider.getInvalidCountryServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    countryFrontService.saveCountryDetails(request));
            assertEquals("No country returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveCountryDetails(any(CountryRequest.class), any());
        }
    }
}
