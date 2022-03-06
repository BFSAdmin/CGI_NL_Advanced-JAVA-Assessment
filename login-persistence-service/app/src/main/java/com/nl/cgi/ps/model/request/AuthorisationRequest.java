package com.nl.cgi.ps.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorisationRequest {

    @JsonProperty(value = "username")
    private String userName;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "firstname")
    private String firstName;

    @JsonProperty(value = "lastname")
    private String lastName;

    @JsonProperty(value = "emailid")
    private String emaildId;

}
