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
public class CountryRequest {
    @JsonProperty(value = "countryname")
    private String countryName;
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "updatename")
    private String updateCountryName;
}
