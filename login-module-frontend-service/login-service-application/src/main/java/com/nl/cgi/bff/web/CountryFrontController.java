package com.nl.cgi.bff.web;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.model.request.CountryRequest;
import com.nl.cgi.bff.model.response.CountryResponse;
import com.nl.cgi.bff.service.CountryFrontService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/login-service/country-details")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class CountryFrontController {

    private final CountryFrontService countryFrontService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve country details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the country details", produces = "application/json", value = "Gets the country details")
    @CrossOrigin(origins = "http://localhost:4200")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "X-AUTH-USER", value = "Encrypted user name", required = true, paramType = "header", dataTypeClass = String.class)})
    public ResponseEntity<CountryResponse> getCountryDetails() {
        log.info("Inside the get country details");
        return ResponseEntity.ok(countryFrontService.getCountryDetails());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "X-AUTH-USER", value = "Encrypted user name", required = true, paramType = "header", dataTypeClass = String.class)})
    @ApiOperation(notes = "save the country details", produces = "application/json", value = "save the country details")
    public ResponseEntity<Boolean> updateCountryDetails(@Valid @RequestBody CountryRequest countryRequest) {
        log.info("Inside requestTransactionReport method call");
        return ResponseEntity.ok(countryFrontService.saveCountryDetails(countryRequest));
    }
}
