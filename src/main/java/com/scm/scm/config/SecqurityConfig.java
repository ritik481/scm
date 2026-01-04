package com.scm.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.scm.services.impl.SecurityCustomUserDetails;

@Configuration
public class SecqurityConfig {

    

    private UserDetails userDetails;

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()
    //     .username("admin123")
    //     .password("admin123")
    //     .roles("ADMIN","USER")
    //     .build();
    //     UserDetails user2= User
    //     .withDefaultPasswordEncoder()
    //     .username("ritik481")
    //     .password("ritik481")
    //     .roles("USER")
    //     .build();
    //    var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetails userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.requestMatchers("/h2-console/**").permitAll();
            authorize.anyRequest().permitAll();

        });
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.headers(headers -> headers.frameOptions().disable());
        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/dashboard");
            // formLogin.failureUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            // formLogin.permitAll();
        });
        httpSecurity.logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
        });
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
