package com.nl.cgi.ps.service;


import com.nl.cgi.ps.dao.repository.UserRepository;
import com.nl.cgi.ps.mockdata.MockDataProvider;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginPersistenceServiceTest {
    LoginPersistenceService loginPersistenceService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        loginPersistenceService = new LoginPersistenceService(userRepository);
    }

    @Nested
    @DisplayName("Get the User Details")
    class RequestGetSaveUserDetails {
        @Test
        void testValidUser() {
            when(userRepository.findLoginsByUsernameAndPassword(any(), any())).thenReturn(Arrays.asList(MockDataProvider.getUserDetails()));
            boolean isValidUser = loginPersistenceService.getUserDetails(MockDataProvider.getLoginServiceRequest("username", "password"));
            assertTrue(isValidUser);
        }
        @Test
        void testInValidUser() {
            when(userRepository.findLoginsByUsernameAndPassword(any(), any())).thenReturn(null);
            boolean isValidUser = loginPersistenceService.getUserDetails(MockDataProvider.getLoginServiceRequest("username", "password"));
            assertFalse(isValidUser);
        }

        @Test
        void testSaveUserDetails() {
            when(userRepository.findLoginsByUsername(any())).thenReturn(Arrays.asList(MockDataProvider.getUserDetails()));
            boolean isUserSaved = loginPersistenceService.saveUserDetails(MockDataProvider.getAuthServiceRequest("username", "password", "firstname","lastname","test@gmail.com"));
            assertFalse(isUserSaved);
        }
        @Test
        void testSaveUserDetailsIfUserAlreadyExits() {
            when(userRepository.findLoginsByUsername(any())).thenReturn(null);
            boolean isUserSaved = loginPersistenceService.saveUserDetails(MockDataProvider.getAuthServiceRequest("usernameexits", "password", "firstname","lastname","test@gmail.com"));
            assertTrue(isUserSaved);
        }

    }
}
