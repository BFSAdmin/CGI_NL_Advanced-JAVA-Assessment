package com.nl.cgi.bff.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "persistence.service")

public class PersistenceServiceProperties extends ServiceProperties {

    private String userAuthenticationUrl;
    private String saveUserDetails;
    private String countryDetails;

}