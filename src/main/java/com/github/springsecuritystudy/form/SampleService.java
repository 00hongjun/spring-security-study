package com.github.springsecuritystudy.form;

import com.github.springsecuritystudy.account.Account;
import com.github.springsecuritystudy.account.AccountContext;
import com.github.springsecuritystudy.common.SecurityLogger;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleService {

    public void dashboard() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();
    }

    public void dashboard2() {
        Account account = AccountContext.getAccount();
        System.out.println("=========" + account.getUsername() + "=========");
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("in Async service");
        log.info("Async service is called");
    }

}
