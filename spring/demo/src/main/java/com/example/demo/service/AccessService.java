package com.example.demo.service;

import com.example.demo.Constants;
import com.example.demo.exception.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("AccessSecurityBean")
class AccessService {
    public boolean hasAdminRights(HttpServletRequest authentication) {
        if (!authentication.isUserInRole(Constants.ADMIN)){
            throw new AccessDeniedException();
        }
        return true;
    }
}

