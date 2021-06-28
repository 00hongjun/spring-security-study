package com.github.springsecuritystudy.form;

import static org.junit.jupiter.api.Assertions.*;

import com.github.springsecuritystudy.account.Account;
import com.github.springsecuritystudy.account.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    SampleService sampleService;
    @Autowired
    AccountService accountService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void dashboard() {
        // given
        Account account = new Account();
        account.setRole("ADMIN");
        account.setUsername("hongjun");
        account.setPassword("123");
        accountService.createNew(account);

        // when
        UserDetails userDetails = accountService.loadUserByUsername("hongjun");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");

        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // then
        sampleService.dashboard();
    }

    @Test
    @WithMockUser
    void dashboard2() {
        // then
        sampleService.dashboard();
    }



}