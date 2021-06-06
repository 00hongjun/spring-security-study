package com.github.springsecuritystudy.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInOutController {

    @GetMapping("/my-login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/my-logout")
    public String logout() {
        return "logout";
    }

}
