package com.example.demo.dao;

import com.example.demo.domain.Authorisation;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorisationRepository extends JpaRepository<Authorisation,Long> {

    Authorisation findByCode(String code);

    @Query("from Authorisation a " +
            "where a.user.username = :username")
    Authorisation findByUsername(@Param("username") String username);

}
