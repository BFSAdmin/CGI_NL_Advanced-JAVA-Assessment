package com.nl.cgi.ps.service;


import com.nl.cgi.ps.dao.model.CountryDetails;
import com.nl.cgi.ps.dao.repository.CountryRepository;
import com.nl.cgi.ps.mockdata.MockDataProvider;
import com.nl.cgi.ps.model.request.CountryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CountryPersistenceServiceTest {
    CountryPersistenceService countryPersistenceService;
    @Mock
    CountryRepository countryRepository;

    @BeforeEach
    public void setup() {
        countryPersistenceService = new CountryPersistenceService(countryRepository);
    }

    @Nested
    @DisplayName("Get and Save the country Details")
    class RequestGetSaveCountryDetails {
        @Test
        void testGetCountryDetails() {
            var countryDetails = CountryDetails.builder().id(1).countryName("INDIA").build();
            when(countryRepository.findAll()).thenReturn(Arrays.asList(countryDetails));
            CountryResponse response = countryPersistenceService.getCountryDetails();
            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals("INDIA", response.getCountryNames().get(0).getCountryName()),
                    () -> assertEquals(1, response.getCountryNames().get(0).getId())
            );
        }

        @Test
        void testGetEmptyCountryDetails() {
            when(countryRepository.findAll()).thenReturn(null);
            CountryResponse response = countryPersistenceService.getCountryDetails();
            assertNotNull(response);
            assertNull(response.getCountryNames());

        }

        @Test
        void testSaveCountryListWhenCountryIsNotFound() {
            var countryDetails = CountryDetails.builder().countryName("USA").build();
            when(countryRepository.findCountryDetailsByCountryName(any())).thenReturn(Optional.empty());
            when(countryRepository.save(countryDetails)).thenReturn(countryDetails);
            countryPersistenceService.saveOrUpdateCountryDetails(MockDataProvider.getCountryServiceRequest());
        }

        @Test
        void testUpdateCountryListWhenCountryFound() {
            var countryDetails = CountryDetails.builder().id(1).countryName("INDIA").build();
            Optional<CountryDetails> list = Optional.of(countryDetails);
            when(countryRepository.findCountryDetailsByCountryName(any())).thenReturn(list);
            countryPersistenceService.saveOrUpdateCountryDetails(MockDataProvider.updateCountryServiceRequest());
        }
    }
}
