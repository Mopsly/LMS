package com.example.demo.service;

import com.example.demo.dao.AuthorisationRepository;
import com.example.demo.domain.Authorisation;
import com.example.demo.dto.UserDto;
import com.example.demo.utils.MapptingUtils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AuthorisationService {

    private final JavaMailSender emailSender;
    private final AuthorisationRepository authRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Value("${register.confirm.url.prefix}")
    private String regUrlPrefix ;

    @Value("${register.confirm.url.suffix}")
    private String regUrlSuffix ;

    @Value("spring.mail.username")
    private String sender;

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
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public String messageToSend(UserDto user, String code){
        return "Привет, "+ user.getUsername()+
                ", для подтверждения регистрации перейди по адресу \n" +
                regUrlPrefix + code + regUrlSuffix;
    }
    public String generateCode(UserDto user){
        String plaintext = user.getUsername();
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String code = bigInt.toString(16);
            Authorisation auth = new Authorisation(user.getId(),code,false,
                    userMapper.mapDtoToUser(userService.findUserByUsername(user.getUsername())),
                    new Timestamp(System.currentTimeMillis()));
            authRepository.save(auth);
            return code;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public UserDto findUserByCode(String code){

        return userMapper.mapUserToDto(authRepository.findByCode(code).getUser());
    }
    public void  authoriseUser(UserDto user){
        Authorisation auth = authRepository.findByUsername(user.getUsername());
        auth.setAuthrorised(true);
        authRepository.save(auth);

    }
}
