package com.github.springsecuritystudy.form;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.springsecuritystudy.account.Account;
import com.github.springsecuritystudy.account.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;

    @DisplayName("/ path는 권한 없이 접근 가능 하다")
    @Test
    void index_anonymous() throws Exception {
        // when
        mockMvc.perform(get("/").with(anonymous()))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/ path는 권한 없이 접근 가능 하다")
    @Test
    @WithAnonymousUser
    void index_anonymous2() throws Exception {
        // when
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/ path는 일반 유저 권한으로 접근 가능하다")
    @Test
    void index_user() throws Exception {
        // when
        mockMvc.perform(get("/").with(user("hong").roles("USER")))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/ path는 일반 유저 권한으로 접근 가능하다")
    @Test
    @WithMockUser(username = "hong", roles = "USER")
    void index_user2() throws Exception {
        // when
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/ path는 일반 유저 권한으로 접근 가능하다")
    @Test
    @WithBasicUser
    void index_user3() throws Exception {
        // when
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/ path는 admin 유저 권한으로 접근 가능하다")
    @Test
    void index_admin() throws Exception {
        // when
        mockMvc.perform(get("/").with(user("hong").roles("ADMIN")))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/admin path는 일반 유저 권한으로 접근 가능하다")
    @Test
    void admin_user() throws Exception {
        // when
        mockMvc.perform(get("/admin").with(user("hong").roles("USER")))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @DisplayName("/admin path는 admin 유저 권한으로 접근 가능하다")
    @Test
    void admin_admin() throws Exception {
        // when
        mockMvc.perform(get("/admin").with(user("hong").roles("ADMIN")))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("/admin path는 admin 유저 권한으로 접근 가능하다")
    @Test
    @WithMockUser(username = "hong", roles = "ADMIN")
    void admin_admin2() throws Exception {
        // when
        mockMvc.perform(get("/admin"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Transactional
    @DisplayName("폼 로그인 테스트")
    @Test
    void login() throws Exception {
        // given
        String username = "hong";
        String password = "123";
        Account account = extractUser(username, password);

        // when
        mockMvc.perform(formLogin().user(username).password(password))
            .andExpect(authenticated());
    }

    @Transactional
    @DisplayName("폼 로그인 테스트 - 로그인 실패")
    @Test
    void login_fail() throws Exception {
        // given
        String username = "hong";
        String password = "123";
        Account account = extractUser(username, password);

        // when
        mockMvc.perform(formLogin().user(username).password("123456"))
            .andExpect(unauthenticated());
    }

    private Account extractUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");

        accountService.createNew(account);
        return account;
    }

}