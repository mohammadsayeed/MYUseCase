package com.klm.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class OauthConfig extends WebSecurityConfigurerAdapter{
	
@Override
protected void configure(HttpSecurity http) throws Exception {
	http.antMatcher("/**")
	.authorizeRequests()
	.antMatchers("/")
	.permitAll()
	.anyRequest()
	.authenticated();
	http.
    anonymous().disable();
	 http.csrf().disable();
}
	
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication() // creating user in memory
            .withUser("zensayeed@gmail.com")
                .password("Test1234").roles("USER")           ;
}

@Override
@Bean
public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
}
}
