package com.nl.cgi.bff.web;

import com.nl.cgi.bff.config.security.JWTAuthorizationFilter;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.AuthorisationRequest;
import com.nl.cgi.bff.model.request.LoginRequest;
import com.nl.cgi.bff.service.LoginService;
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
class LoginFrontEndControllerTest {
    private static final String LOGIN_URI = "/login-service/authorization";
    private static final String SIGNUP_URI = "/login-service/user-details";
    @Mock
    LoginService loginService;

    private MockMvc mockMvc;
    @Mock
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @BeforeEach
    public void setup() {
        LoginFrontEndController stateController = new LoginFrontEndController(loginService, jwtAuthorizationFilter);
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .build();

    }

    @Nested
    @DisplayName("Get get and save call all scenario")
    class TestUserLoginAndSave {
        @Test
        void testLogin() throws Exception {
            when(loginService.isValidUserDetails(any(LoginRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getLoginRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginService, times(1)).isValidUserDetails(any(LoginRequest.class));
        }

        @Test
        void testSaveUserDetails() throws Exception {
            when(loginService.saveUserDetails(any(AuthorisationRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getSaveUserDetailsRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginService, times(1)).saveUserDetails(any(AuthorisationRequest.class));
        }
    }

    @Nested
    @DisplayName("Get User details - Service Exceptions")
    class GetUserDetailsServiceExceptions {
        @Test
        void checkExceptionThrownWhenAuthRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenUserNameISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest(null, "password").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenPassWordISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest("username", null).toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenUserNameISEmpty() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest("", "password").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenPassWordISEmpty() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest("username", "").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("Save User Details - Service Exceptions")
    class SaveUserDetailsReportServiceExceptions {

        @Test
        void checkExceptionThrownWhenAuthRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenUserNameISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getInvalidLSaveRequest(null, "password", "firsname", "lastname", "test@gmai.com").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenPassWordISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getInvalidLSaveRequest("userName", null, "firsname", "lastname", "test@gmai.com").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }

    @Test
    void checkExceptionThrownWhenFirstNameNull() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .content(MockDataProvider.getInvalidLSaveRequest("userName", "password", null, "lastname", "test@gmai.com").toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void checkExceptionThrownWhenLastNameNull() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .content(MockDataProvider.getInvalidLSaveRequest("userName", "password", "firstname", null, "test@gmai.com").toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void checkExceptionThrownWhenEmailNull() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .content(MockDataProvider.getInvalidLSaveRequest("userName", "password", "firstname", "lastname", null).toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


}



