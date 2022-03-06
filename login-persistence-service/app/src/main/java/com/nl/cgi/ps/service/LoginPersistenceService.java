package com.nl.cgi.ps.service;


import com.nl.cgi.ps.dao.model.UserDetails;
import com.nl.cgi.ps.dao.repository.UserRepository;
import com.nl.cgi.ps.model.request.AuthorisationRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class LoginPersistenceService {

    private final UserRepository userRepository;

    /**
     *
     * @param request
     * @return
     */
    public boolean getUserDetails(AuthorisationRequest request) {
        List<UserDetails> userDetails = userRepository.findLoginsByUsernameAndPassword(request.getUserName(), request.getPassword());
        log.debug("user details {}", userDetails);
        return (!CollectionUtils.isEmpty(userDetails));
    }

    /**
     *
     * @param request
     * @return
     */
    public boolean saveUserDetails(AuthorisationRequest request) {
        List<UserDetails> userDetails = userRepository.findLoginsByUsername(request.getUserName());
        log.debug("user details {}", userDetails);
        if (CollectionUtils.isEmpty(userDetails)) {
            userRepository.save(buildUserDetails(request));
            log.debug("user details saved successfully{}", userDetails);
            return true;
        } else {
            log.debug("user already exits{}", userDetails);
            return false;
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private UserDetails buildUserDetails(AuthorisationRequest request) {
        return UserDetails.builder()
                .username(request.getUserName())
                .password(request.getPassword())
                .emailId(request.getEmaildId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName()).build();
    }


}
