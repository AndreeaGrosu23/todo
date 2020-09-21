package com.example.todo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenServices jwtTokenServices;

    public SecurityConfig(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//                .formLogin().loginPage("/login.html")
//                .loginProcessingUrl("/auth/signin")
//                .defaultSuccessUrl("/index.html")
//                .permitAll()
//            .and()
//                .logout()
//                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers( "/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/hello").authenticated()
                .antMatchers(HttpMethod.POST, "/list").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/todos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/todos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/todos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/addTodo").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/me").hasRole("ADMIN")
                .anyRequest().denyAll()// anything else is denied
            .and()
            .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);
    }
}
