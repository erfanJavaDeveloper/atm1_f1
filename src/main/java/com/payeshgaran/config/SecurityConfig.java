package com.payeshgaran.config;

import com.payeshgaran.security.UserManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.payeshgaran.entity.permission.Permission.*;
import static com.payeshgaran.entity.permission.Role.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserManagement userManagement;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/account/**").hasAnyRole(USER.name(), ADMIN.name() )
//                .antMatchers(HttpMethod.POST, "/account/**").hasAnyAuthority( WRITE_USER.getPermissionName() )
//                .antMatchers(HttpMethod.PUT, "/account/**").hasAnyAuthority(READ_USER.getPermissionName(), WRITE_USER.getPermissionName() )
//                .antMatchers(HttpMethod.DELETE, "/account/**").hasAnyAuthority(READ_USER.getPermissionName(), WRITE_USER.getPermissionName() )
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
//        READ_USER.getPermissionName(),
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userManagement);
        return provider;
    }

    //todo ******************************
    //todo inmemory userditailService
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails erfan = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("123"))
//                .authorities(USER.getAuthority())
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("123"))
//                .authorities(ADMIN.getAuthority())
//                .build();
//
//        return new InMemoryUserDetailsManager(erfan, admin);
//    }
}
