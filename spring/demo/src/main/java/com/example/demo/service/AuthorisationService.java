package com.example.demo.service;

import com.example.demo.dao.AuthorisationRepository;
import com.example.demo.domain.Authorisation;
import com.example.demo.dto.UserDto;
import com.example.demo.utils.MapptingUtils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AuthorisationService {

    private final JavaMailSender emailSender;
    private final AuthorisationRepository authRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthorisationService(JavaMailSender emailSender, AuthorisationRepository authRepository, UserService userService, UserMapper userMapper) {
        this.emailSender = emailSender;
        this.authRepository = authRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public void sendMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lms.teta.summer@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public String messageToSend(UserDto user, String code){
        return "Привет, "+ user.getUsername()+
                ", для подтверждения регистрации перейди по адресу \n" +
                "http://localhost:8080/register/"+ code +"/success";
    }
    public String generateCode(UserDto user){
        String code = UUID.randomUUID().toString();
        Authorisation auth = new Authorisation(user.getId(),code,false,
                userMapper.mapDtoToUser(userService.getUserByUsername(user.getUsername())),
                new Timestamp(System.currentTimeMillis()));
        authRepository.save(auth);
        return code;
    }
    public UserDto findUserByCode(String code){

        return userMapper.mapUserToDto(authRepository.findByCode(code).getUser());
    }
    public void  authoriseUser(UserDto user){
        Authorisation auth = authRepository.findByUser_Username(user.getUsername());
        auth.setAuthrorised(true);
        authRepository.save(auth);

    }
}
