package com.nl.cgi.ps.web;


import com.nl.cgi.ps.model.request.AuthorisationRequest;
import com.nl.cgi.ps.service.LoginPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/login-persistence-service")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)

public class LoginPersistenceController {

    private final LoginPersistenceService loginPersistenceService;

    /**
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/verify/userdetails")
    public ResponseEntity<Boolean> getUserDetails(@RequestBody AuthorisationRequest loginRequest) {
        log.info("Inside requestTransactionReport method call");
        return ResponseEntity.ok(loginPersistenceService.getUserDetails(loginRequest));
    }

    /**
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/save/userdetails")
    public ResponseEntity<Boolean> saveUserDetails(@RequestBody AuthorisationRequest loginRequest) {
        log.info("Inside requestTransactionReport method call");
        return ResponseEntity.ok(loginPersistenceService.saveUserDetails(loginRequest));
    }

}
