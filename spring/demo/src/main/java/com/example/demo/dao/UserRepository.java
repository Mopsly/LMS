package com.example.demo.dao;

import com.example.demo.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u " +
            "where u.id not in " +
            "( select u.id " +
            "from User u left join u.courses c " +
            "where c.id = :courseId)")
    List<User> findUsersNotAssignedToCourse(@Param("courseId") long courseId);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    @Query("from User u " +
            "where u.username = :username AND u.authorisation.authrorised = true")
    Optional<User> findAuthorisedUserByUsername(@Param("username") String username);

}
