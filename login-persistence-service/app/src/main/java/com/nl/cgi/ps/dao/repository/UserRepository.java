package com.nl.cgi.ps.dao.repository;

import com.nl.cgi.ps.dao.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public List<UserDetails> findLoginsByUsernameAndPassword(String username, String password);

    /**
     *
     * @param username
     * @return
     */
    public List<UserDetails> findLoginsByUsername(String username);
}