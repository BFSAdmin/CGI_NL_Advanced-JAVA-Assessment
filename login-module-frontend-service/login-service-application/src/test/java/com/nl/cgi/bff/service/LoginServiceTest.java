package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.AuthorisationRequest;
import com.nl.cgi.bff.model.request.LoginRequest;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginServiceTest {
    LoginService loginService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        loginService = new LoginService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("Get the User Details")
    class RequestGetUserDetails {
    @Test
    void testValidUser() {
        when(persistenceServiceClient.getUserDetails(any(LoginRequest.class), any())).thenReturn(true);
        boolean isValidUser = loginService.isValidUserDetails(MockDataProvider.getLoginServiceRequest("testuser", "testpass"));
        assertTrue(isValidUser);
        verify(persistenceServiceClient, times(1)).getUserDetails(any(LoginRequest.class), any());
    }


    @Test
    void testInValidUser() {
        when(persistenceServiceClient.getUserDetails(any(LoginRequest.class), any())).thenThrow(new ServiceException("Invalid User"));
        LoginRequest request = MockDataProvider.getLoginServiceRequest("invalidUser", "testpass");
        ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                loginService.isValidUserDetails(request));
        verify(persistenceServiceClient, times(1)).getUserDetails(any(LoginRequest.class), any());
        assertEquals("Invalid User", invalidRequestException.getMessage());
    }

    }

    @Nested
    @DisplayName("Save the User Details")
    class RequestSaveUserDetails {
        @Test
        void testSaveUserDetails() {
            when(persistenceServiceClient.saveUserDetails(any(AuthorisationRequest.class), any())).thenReturn(true);
            boolean IsUserSaved = loginService.saveUserDetails(MockDataProvider.getAuthServiceRequest("testuser", "testpass","firstname","lastname","emailId"));
            assertTrue(IsUserSaved);
            verify(persistenceServiceClient, times(1)).saveUserDetails(any(AuthorisationRequest.class), any());
        }


        @Test
        void testUserAlreadyExits() {
            when(persistenceServiceClient.saveUserDetails(any(AuthorisationRequest.class), any())).thenThrow(new ServiceException("User already Exits"));
            AuthorisationRequest request =MockDataProvider.getAuthServiceRequest("userExits", "testpass","firstname","lastname","emailId");
            ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                    loginService.saveUserDetails(request));
            verify(persistenceServiceClient, times(1)).saveUserDetails(any(AuthorisationRequest.class), any());
            assertEquals("User already Exits", invalidRequestException.getMessage());
        }

    }
}
