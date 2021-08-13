package com.example.demo.controller;

import com.example.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    public ExceptionController() {
    }

    @ExceptionHandler({NotFoundException.class})
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ModelAndView noPermissionExceptionHandler(AccessDeniedException e) {
        ModelAndView modelAndView = new ModelAndView("access_denied");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        return modelAndView;
    }

    @ExceptionHandler({LoginFailedException.class})
    public ModelAndView loginFailedException(LoginFailedException e) {
        ModelAndView modelAndView = new ModelAndView("access_denied");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        return modelAndView;
    }
    @ExceptionHandler({InternalServerError.class})
    public ModelAndView serviceErrorException(InternalServerError e) {
        ModelAndView modelAndView = new ModelAndView("access_denied");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        return modelAndView;
    }

    @ExceptionHandler(MediaNotFoundException.class)
    public ResponseEntity<Void> mediaNotFoundExceptionHandler(MediaNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
