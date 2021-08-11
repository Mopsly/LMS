package com.example.demo.service;

import com.example.demo.Constants;
import com.example.demo.dao.CourseRepository;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.AccessService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertThrows;

public class AccessServiceTest {


    HttpServletRequest requestMock;
    @BeforeEach
    public void setUp(){
        requestMock = Mockito.mock(HttpServletRequest.class);

    }

    @Test
    public void hasAdminRightsTest(){
        Mockito.when(requestMock.isUserInRole(Constants.ADMIN)).thenReturn(true);
        AccessService service = new AccessService();
        Assertions.assertThat(service.hasAdminRights(requestMock)).isTrue();
        }

    @Test
    public void hasNoAdminRightsTest(){
        Mockito.when(requestMock.isUserInRole(Constants.ADMIN)).thenReturn(false);
        AccessService service = new AccessService();
        assertThrows(AccessDeniedException.class, () -> service.hasAdminRights(requestMock));
    }
}

