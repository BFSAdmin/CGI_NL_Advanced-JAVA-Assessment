package com.nl.cgi.ps.web;

import com.nl.cgi.ps.model.request.CountryRequest;
import com.nl.cgi.ps.model.request.CountryResponse;
import com.nl.cgi.ps.service.CountryPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/login-persistence-service/country-details")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class CountryPersistenceController {

    private final CountryPersistenceService countryPersistenceService;

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<CountryResponse> getUserDetails() {
        log.info("Inside requestTransactionReport method call");
             CountryResponse response=countryPersistenceService.getCountryDetails();
        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param countryRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<Boolean> saveUserDetails(@RequestBody CountryRequest countryRequest) {
        log.info("Inside requestTransactionReport method call");
        countryPersistenceService.saveOrUpdateCountryDetails(countryRequest);
        return ResponseEntity.ok(true);
    }
}
