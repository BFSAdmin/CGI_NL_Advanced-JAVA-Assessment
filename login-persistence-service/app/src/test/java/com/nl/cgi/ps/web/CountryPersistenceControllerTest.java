package com.nl.cgi.ps.web;

import com.nl.cgi.ps.mockdata.MockDataProvider;
import com.nl.cgi.ps.service.CountryPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc()
public class CountryPersistenceControllerTest {

    private static final String COUNTRY_URI = "/login-persistence-service/country-details";

    @Mock
    CountryPersistenceService countryPersistenceService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        CountryPersistenceController loginPersistenceController = new CountryPersistenceController(countryPersistenceService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginPersistenceController)
                .build();
    }

    @Nested
    @DisplayName("Get get and save call all scenario")
    class TestGetSaveCountryDetails {
        @Test
        void testGetCountryDetails() throws Exception {
            when(countryPersistenceService.getCountryDetails()).thenReturn(MockDataProvider.getCountryDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(COUNTRY_URI)
                            .content(MockDataProvider.getLoginRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(countryPersistenceService, times(1)).getCountryDetails();
        }

        @Test
        void testSaveCountryDetails() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(COUNTRY_URI)
                            .content(MockDataProvider.getSaveUserDetailsRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(countryPersistenceService, times(1)).saveOrUpdateCountryDetails(any());
        }

        @Test
        void testCountryDetailsWhenServiceReturnEmptyList() throws Exception {
            when(countryPersistenceService.getCountryDetails()).thenReturn(null);
            mockMvc
                    .perform(MockMvcRequestBuilders.get(COUNTRY_URI)
                            .content(MockDataProvider.getLoginRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(countryPersistenceService, times(1)).getCountryDetails();
        }
    }
}
