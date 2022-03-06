package com.nl.cgi.ps.service;


import com.nl.cgi.ps.dao.model.CountryDetails;
import com.nl.cgi.ps.dao.repository.CountryRepository;
import com.nl.cgi.ps.model.request.CountryRequest;
import com.nl.cgi.ps.model.request.CountryResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class CountryPersistenceService {

    private final CountryRepository countryRepository;

    /**
     *
     * @return
     */
    public CountryResponse getCountryDetails() {
        return CountryResponse.builder().countryNames(countryRepository.findAll()).build();
    }

    /**
     *
     * @param request
     */
    public void saveOrUpdateCountryDetails(CountryRequest request) {
        var countryDetails = StringUtils.isNotEmpty(request.getCountryName()) ? countryRepository.findCountryDetailsByCountryName(request.getCountryName()) : null;
        log.debug("country details is not found and save the country{}",request.getCountryName());
        if (countryDetails != null && countryDetails.isPresent()) {
            CountryDetails details = countryDetails.get();
            details.setCountryName(StringUtils.isNotEmpty(request.getUpdateCountryName()) ? request.getUpdateCountryName() : request.getCountryName());
            countryRepository.save(details);
            log.debug("country details updated successfully {}",request.getCountryName());
        } else {
            countryRepository.save(buildUserDetails(request));
            log.debug("country details saved successfully {}",request.getCountryName());
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private CountryDetails buildUserDetails(CountryRequest request) {
        return CountryDetails.builder().countryName(request.getCountryName()).build();
    }


}
