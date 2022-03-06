package com.nl.cgi.bff.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nl.cgi.bff.exception.ExceptionResponse;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.mockdata.StubServerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PersistenceServiceClientTest extends StubServerConfig {
    PersistenceServiceClient persistenceServiceClient;

    @BeforeEach
    void setup() {
        persistenceServiceClient = new PersistenceServiceClient(webClient);
    }

    @Nested
    @DisplayName("Test Get Service Details")
    class TestGetUserDetailsPSService {
        @Test
        void testGetUserDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean isValidUser = persistenceServiceClient.getUserDetails(MockDataProvider.getLoginServiceRequest("username", "password"), "/userdetails");
            assertTrue(isValidUser);
        }


        @Test
        void testGetBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getLoginServiceRequest("username", "password");
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.getUserDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during get user details call", exception.getMessage());

        }

        @Test
        void testGetInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getLoginServiceRequest("username", "password");
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.getUserDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during get user details call", exception.getMessage());

        }


    }

    @Nested
    @DisplayName("Test Save Service Details")
    class TestSaveUserDetailsPSService {
        @Test
        void testSaveUserDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean isUserSaved = persistenceServiceClient.saveUserDetails(MockDataProvider.getAuthServiceRequest("username", "password", "ffd","dfd","sdsd@gmail.com"), "/userdetails");
            assertTrue(isUserSaved);
        }

        @Test
        void testUserDetailsIsExits() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("false"));
            var request = MockDataProvider.getAuthServiceRequest("username", "password", "ffd","dfd","sdsd@gmail.com");
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveUserDetails(request, "url")
            );
            assertEquals("User is already exists", exception.getErrorDetail().getErrorMessage());
            assertEquals("user returned from persistence service", exception.getMessage());

        }

        @Test
        void testSaveBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getAuthServiceRequest("username", "password", "ffd","dfd","sdsd@gmail.com");
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveUserDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save user details call", exception.getMessage());

        }

        @Test
        void testSaveInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getAuthServiceRequest("username", "password", "ffd","dfd","sdsd@gmail.com");
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveUserDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save user details call", exception.getMessage());

        }


    }

    @Nested
    @DisplayName("Test Get Save Service for country Details")
    class TestSaveCountryPSService {
        @Test
        void testSaveCountryDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean isCountrySaved= persistenceServiceClient.saveCountryDetails(MockDataProvider.getCountryServiceRequest(),"/save-country");
            assertTrue(isCountrySaved);
        }

        @Test
        void testSaveCountryDetailsWhenTheRequestISNull() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("false"));
            var request = MockDataProvider.getInvalidCountryServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveCountryDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Country details is not saved", exception.getMessage());

        }

        @Test
        void testSaveCountryBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getInvalidCountryServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveCountryDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save country details call", exception.getMessage());

        }

        @Test
        void testSaveCountryInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getInvalidCountryServiceRequest();
                    ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveCountryDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save country details call", exception.getMessage());

        }


    }




}
