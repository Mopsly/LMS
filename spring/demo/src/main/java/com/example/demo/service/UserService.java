package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private  final UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public User userById(Long id){
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }
    public List<User> allUsers(){
        return repository.findAll();
    }
    public List<User> unassignedUsers(Long courseId){
        return repository.findUsersNotAssignedToCourse(courseId);
    }


}
