package com.nl.cgi.ps.model.request;

import com.nl.cgi.ps.dao.model.CountryDetails;
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
