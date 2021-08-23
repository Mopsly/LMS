package com.example.demo.service;

import com.example.demo.Constants;
import com.example.demo.domain.Module;
import com.example.demo.exception.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.example.demo.Constants.ADMIN;

@Component("AccessSecurityBean")
public class AccessService {

    private final ModuleService moduleService;
    private final UserService userService;

    @Autowired
    public AccessService(ModuleService moduleService, UserService userService) {
        this.moduleService = moduleService;
        this.userService = userService;
    }

    public boolean hasAdminRights(HttpServletRequest authentication) {
        if (!authentication.isUserInRole(Constants.ADMIN)){
            throw new AccessDeniedException();
        }
        return true;
    }

    public boolean hasAccessToModule(HttpServletRequest request, Long moduleId) {
        if (request.isUserInRole(ADMIN)) {
            return true;
        }
        Module module = moduleService.findById(moduleId);
        if (userService.unassignedUsers(module.getCourse().getId())
                .contains(userService.findByUsername(request.getRemoteUser()))) {
            throw new AccessDeniedException();
        }
        return true;
    }
}

