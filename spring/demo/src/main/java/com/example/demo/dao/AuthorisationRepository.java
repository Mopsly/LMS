package com.example.demo.dao;

import com.example.demo.domain.Authorisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorisationRepository extends JpaRepository<Authorisation,Long> {
    Authorisation findByCode(String code);
    Authorisation findByUser_Username(String username);
}
