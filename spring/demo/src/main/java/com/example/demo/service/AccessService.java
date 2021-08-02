package com.example.demo.service;

import com.example.demo.exception.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("AccessSecurityBean")
class AccessService {
    public boolean hasAccessToDeleteCourse(HttpServletRequest authentication) {
        if (!authentication.isUserInRole("ROLE_ADMIN")){
            throw new AccessDeniedException();
        }
        return true;
    }
}

