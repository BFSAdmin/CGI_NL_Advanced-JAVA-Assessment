package com.nl.cgi.ps.web;

import com.nl.cgi.ps.mockdata.MockDataProvider;
import com.nl.cgi.ps.model.request.AuthorisationRequest;
import com.nl.cgi.ps.service.LoginPersistenceService;
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
class LoginPersistenceControllerTest {
    private static final String LOGIN_URI = "/login-persistence-service/verify/userdetails";
    private static final String SIGNUP_URI = "/login-persistence-service/save/userdetails";
    @Mock
    LoginPersistenceService loginPersistenceService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        LoginPersistenceController loginPersistenceController = new LoginPersistenceController(loginPersistenceService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginPersistenceController)
                .build();
    }
    @Nested
    @DisplayName("Get get and save call all scenario")
    class TestUserLoginAndSave {
        @Test
        void testLogin() throws Exception {
            when(loginPersistenceService.getUserDetails(any(AuthorisationRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getLoginRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginPersistenceService, times(1)).getUserDetails(any(AuthorisationRequest.class));
        }

        @Test
        void testSaveUserDetails() throws Exception {
            when(loginPersistenceService.saveUserDetails(any(AuthorisationRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getSaveUserDetailsRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginPersistenceService, times(1)).saveUserDetails(any(AuthorisationRequest.class));
        }

        @Test
        void testLoginWhenUserDetailsISNotFound() throws Exception {
            when(loginPersistenceService.getUserDetails(any(AuthorisationRequest.class))).thenReturn(false);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getLoginRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginPersistenceService, times(1)).getUserDetails(any(AuthorisationRequest.class));
        }

        @Test
        void testSaveUserDetailsWhenUserAlreadyExits() throws Exception {
            when(loginPersistenceService.saveUserDetails(any(AuthorisationRequest.class))).thenReturn(false);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getSaveUserDetailsRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginPersistenceService, times(1)).saveUserDetails(any(AuthorisationRequest.class));
        }
    }


}



