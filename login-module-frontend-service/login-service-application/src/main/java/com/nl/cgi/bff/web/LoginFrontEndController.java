package com.nl.cgi.bff.web;

import com.nl.cgi.bff.config.security.JWTAuthorizationFilter;
import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.model.request.AuthorisationRequest;
import com.nl.cgi.bff.model.request.LoginRequest;
import com.nl.cgi.bff.model.response.AuthorisationResponse;
import com.nl.cgi.bff.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class LoginFrontEndController {


    private final LoginService loginService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    /**
     * @param loginRequest
     * @return
     */

    @PostMapping(value = "/login-service/authorization", consumes = "application/json")
    @ApiOperation(notes = "Gets the authorized details of the customer", produces = "application/json", value = "Gets the authorized details of the customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AuthorisationResponse> getUserDetails(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Inside login method call and user name {}", loginRequest.getUserName());
        loginService.isValidUserDetails(loginRequest);
        return ResponseEntity.ok(AuthorisationResponse.builder().token(jwtAuthorizationFilter.getJWTToken(loginRequest.getUserName())).build());

    }

    /**
     * @param loginRequest
     * @return
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login-service/user-details", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    public ResponseEntity<Boolean> saveUserDetails(@Valid @RequestBody AuthorisationRequest loginRequest) {
        log.info("Inside save method call and user name {}", loginRequest.getUserName());
        loginService.saveUserDetails(loginRequest);
        return ResponseEntity.ok(true);

    }
}