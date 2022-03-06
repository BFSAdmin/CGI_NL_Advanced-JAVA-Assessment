package com.nl.cgi.ps.mockdata;

import com.google.gson.JsonObject;
import com.nl.cgi.ps.dao.model.CountryDetails;
import com.nl.cgi.ps.dao.model.UserDetails;
import com.nl.cgi.ps.model.request.AuthorisationRequest;
import com.nl.cgi.ps.model.request.CountryRequest;
import com.nl.cgi.ps.model.request.CountryResponse;


import java.util.Arrays;

public class MockDataProvider {

    public static JsonObject getLoginRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("username", "johnuser")
                .withProperty("password", "somepassword")
                .build();
    }

    public static JsonObject getSaveUserDetailsRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("username", "johnuser")
                .withProperty("password", "somepassword")
                .withProperty("firstname", "firstname")
                .withProperty("lastname", "lastname")
                .withProperty("emailid", "test@gmail.com")
                .build();
    }

    public static JsonObject getInvalidLoginRequest(String userName, String password) {
        return MockJsonBuilder.aRequest()
                .withProperty("username", userName)
                .withProperty("password", password)
                .build();
    }

    public static JsonObject getInvalidLSaveRequest(String userName, String password, String firstname, String lastname, String emailId) {
        return MockJsonBuilder.aRequest()
                .withProperty("username", userName)
                .withProperty("password", password)
                .withProperty("firstname", firstname)
                .withProperty("lastname", lastname)
                .withProperty("emailid", emailId)
                .build();
    }

    public static AuthorisationRequest getLoginServiceRequest(String userName, String password) {
        return AuthorisationRequest.builder().userName(userName).password(password).build();
    }

    public static AuthorisationRequest getAuthServiceRequest(String userName, String password, String firstName, String lastName, String emailId) {
        return AuthorisationRequest.
                builder().
                userName(userName).
                password(password)
                .firstName(firstName)
                .lastName(lastName)
                .emaildId(emailId)
                .build();
    }

    public static CountryResponse getCountryDetails() {
        var countryDetails1 = CountryDetails.builder().id(1).countryName("INDIA").build();
        var countryDetails2 = CountryDetails.builder().id(2).countryName("USA").build();
        return CountryResponse.builder().countryNames(Arrays.asList(countryDetails1, countryDetails2)).build();
    }

    public static JsonObject getCountrySaveRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("countryname", "INDIA")
                .build();
    }

    public static JsonObject getInvalidCountrySaveRequest() {
        return MockJsonBuilder.aRequest()
                .build();
    }

    public static JsonObject getCountryUpdateRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("id", "1")
                .withProperty("countryname", "INDIA")
                .withProperty("updatedname", "INDIA")
                .build();
    }

    public static CountryRequest getCountryServiceRequest() {
        return CountryRequest.builder().countryName("INDIA").build();
    }
    public static CountryRequest updateCountryServiceRequest() {
        return CountryRequest.builder()
                .id(1l)
                .countryName("INDIA")
                .updateCountryName("USA")
                .build();
    }

    public static CountryRequest getInvalidCountryServiceRequest() {
        return CountryRequest.builder().build();
    }

    public static UserDetails getUserDetails(){
        return UserDetails.builder().
                username("test")
                .lastName("user")
                .password("dddd")
                .firstName("Firtname")
                .lastName("Jond")
                .emailId("test@gmail.com")
                .build();
    }

}
