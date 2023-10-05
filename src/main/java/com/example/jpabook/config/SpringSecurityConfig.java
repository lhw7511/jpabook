package com.example.jpabook.config;

import com.example.jpabook.auth.CustomAuthFailureHandler;
import com.example.jpabook.domain.Member;
import com.example.jpabook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    private MemberService memberService;

    @Bean
    public CustomAuthFailureHandler customAuthFailureHandler(){return new CustomAuthFailureHandler();}
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/orders","/items","/members","/items/new").hasRole("ADMIN")
                    .antMatchers("/order").authenticated()
                    .antMatchers("/members/new").anonymous()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .loginPage("/loginForm")
                    .usernameParameter("email")			// 아이디 파라미터명 설정
                    .passwordParameter("pw")
                    .defaultSuccessUrl("/", true)
                    .failureForwardUrl("/loginForm")
                    .failureHandler(customAuthFailureHandler())
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/accessDenied")
                .and()
                .rememberMe()
                .key("remember-Me")
                .rememberMeParameter("remember")
                .tokenValiditySeconds(86400)
                .userDetailsService(memberService);


    }

    //web자원은 필터링 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}
