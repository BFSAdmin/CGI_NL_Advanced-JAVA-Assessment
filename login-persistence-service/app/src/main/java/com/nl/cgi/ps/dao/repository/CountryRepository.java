package com.nl.cgi.ps.dao.repository;

import com.nl.cgi.ps.dao.model.CountryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryDetails, Long> {

    public Optional<CountryDetails> findCountryDetailsByCountryName(String countryname);

}
