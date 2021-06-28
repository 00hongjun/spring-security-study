package com.github.springsecuritystudy.account;

import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserAccount extends User {

    @Getter
    private Account account;

    public UserAccount(Account account) {
        super(account.getUsername(), account.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole())));

        this.account = account;
    }

}
