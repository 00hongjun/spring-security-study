package com.github.springsecuritystudy.account;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("가입페이지 보여주기")
    @Test
    void signUpForm() throws Exception {
        // then
        mockMvc.perform(get("/signup"))
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.containsString("_csrf")));
    }

    @DisplayName("가입 과정 테스트")
    @Test
    void processSignUp() throws Exception {
        // given
        mockMvc.perform((post("/signup")
            .param("username", "hongjun")
            .param("password", "123")
            .with(csrf())))
            .andDo(print())
            .andExpect(status().is3xxRedirection());

        // when

        // then
    }

}