package com.github.springsecuritystudy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityLogger {

    public static void log(String message) {
        log.info("message : {} =======================", message);
        Thread thread = Thread.currentThread();
        log.info("thread: {}", thread);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal: {}", principal);
    }

}
