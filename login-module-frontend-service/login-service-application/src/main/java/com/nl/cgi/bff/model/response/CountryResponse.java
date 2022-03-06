package com.nl.cgi.bff.model.response;

import com.nl.cgi.bff.model.request.CountryDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryResponse {
    List<CountryDetails> countryNames;
}
