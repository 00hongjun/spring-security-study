package com.github.springsecuritystudy.form;

import com.github.springsecuritystudy.account.Account;
import com.github.springsecuritystudy.account.AccountContext;
import com.github.springsecuritystudy.account.AccountRepository;
import com.github.springsecuritystudy.account.UserAccount;
import com.github.springsecuritystudy.common.CurrentUser;
import com.github.springsecuritystudy.common.SecurityLogger;
import java.security.Principal;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;
    private final AccountRepository accountRepository;

    @GetMapping("/")
//    public String index(Model model, Principal principal) {
//    public String index(Model model, @AuthenticationPrincipal UserAccount userAccount) {
//    public String index(Model model, @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") Account userAccount) {
    public String index(Model model, @CurrentUser Account userAccount) {

//        if (principal == null) {
        if (userAccount == null) {
            model.addAttribute("message", "hello spring security");
        } else {
//            model.addAttribute("message", "hello " + principal.getName());
            model.addAttribute("message", "hello " + userAccount.getUsername());
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
        model.addAttribute("message", "hello Dashboard : " + principal.getName());
        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()).get());
        sampleService.dashboard();
        sampleService.dashboard2();
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "hello Admin : " + principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "hello User : " + principal.getName());
        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");

        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Handler";
    }

}
