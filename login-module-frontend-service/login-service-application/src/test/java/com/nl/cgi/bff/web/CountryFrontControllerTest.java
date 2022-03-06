package com.nl.cgi.bff.web;

import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.CountryRequest;
import com.nl.cgi.bff.service.CountryFrontService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
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
public class CountryFrontControllerTest {

    private static final String URI = "/login-service/country-details";
    private static final String X_AUTH_USER = "X-AUTH-USER";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsd2lsbGlhbXMxNiIsInJvbGVzIjoidXNlciIsImlhdCI6MTUxNDQ0OTgzM30.WKMQ_oPPiDcc6sGtMJ1Y9hlrAAc6U3xQLuEHyAnM1FU";

    @Mock
    CountryFrontService countryFrontService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        CountryFrontController stateController = new CountryFrontController(countryFrontService);
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .build();

    }

    @Nested
    @DisplayName("Country get and save call all scenario")
    class TestGetCountryAndSaveCountryDetails {
        @Test
        void testGetCountryDetails() throws Exception {
            when(countryFrontService.getCountryDetails()).thenReturn(MockDataProvider.getCountryDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(countryFrontService, times(1)).getCountryDetails();
        }

        @Test
        void testSaveCountryDetails() throws Exception {
            when(countryFrontService.saveCountryDetails(any(CountryRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .content(MockDataProvider.getCountrySaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(countryFrontService, times(1)).saveCountryDetails(any(CountryRequest.class));
        }

        @Test
        void testUpdateCountryDetails() throws Exception {
            when(countryFrontService.saveCountryDetails(any(CountryRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .content(MockDataProvider.getCountryUpdateRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(countryFrontService, times(1)).saveCountryDetails(any(CountryRequest.class));
        }
    }

    @Nested
    @DisplayName("Get country details - Service Exceptions")
    class GetAndSaveCountryServiceExceptions {
        @Test
        void checkExceptionThrownWhenRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenRequestHeaderISNUll() throws Exception {
            try {
                when(countryFrontService.getCountryDetails()).thenThrow(new AccessDeniedException("Token is not found"));
                mockMvc
                        .perform(MockMvcRequestBuilders.get(URI)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            } catch (Exception e) {

            }
        }

        @Test
        void checkSaveExceptionThrownWhenCountryNameIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .content(MockDataProvider.getInvalidCountrySaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }
}
