package com.example.demo.security;


import com.example.demo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                //open security
//                .authorizeRequests().anyRequest().permitAll();

                //disable CSRF for Postman usage
                .csrf().disable()
                //all requests require authorization
                .authorizeRequests()
                //allow all requests to read recipes and reviews
                .antMatchers(HttpMethod.GET,  "/", "/webjars/**", "/css/**", "/login/**", "/images/**",
                        "/register", "/userinfo", "/edit-user", "/myrental").permitAll()
                //allow all requests to create a user account or post review
                .antMatchers("/employeePortal", "/instruments","/instruments/new")
                .access("hasRole('ADMIN')")
                //all other requests should be authenticated
//                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SecUserDetailsService secUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //use a customUserDetailService and a BCryptPasswordEncoder to set up an AuthenticationManager.
        auth.userDetailsService(secUserDetailsService).passwordEncoder(passwordEncoder);
    }



}