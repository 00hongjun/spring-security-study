package com.github.springsecuritystudy.form;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "hong", roles = "USER")
public @interface WithBasicUser {

}
