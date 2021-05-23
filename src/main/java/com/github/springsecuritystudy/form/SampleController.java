package com.github.springsecuritystudy.form;

import com.github.springsecuritystudy.account.AccountContext;
import com.github.springsecuritystudy.account.AccountRepository;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;
    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal) {

        if (principal == null) {
            model.addAttribute("message", "hello spring security");
        } else {
            model.addAttribute("message", "hello " + principal.getName());
        }

        return "index";
    }

    @GetMapping("/info")
    public String info(Model model, Principal principal) {
        model.addAttribute("message", "Info");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "hello Admin" + principal.getName());
        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()).get());
        sampleService.dashboard();
        sampleService.dashboard2();
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "hello Admin" + principal.getName());
        return "admin";
    }

}
