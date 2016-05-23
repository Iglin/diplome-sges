package ru.ssk.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by user on 13.05.2016.
 */
@Configuration
@EnableWebSecurity
@Import({ DataConfig.class })
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT login, password, true FROM \"sys_user\" WHERE login=?")
                .authoritiesByUsernameQuery(
                        "SELECT login, role FROM \"sys_user\" WHERE login=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/main/**").authenticated()
                .and()
                .formLogin().loginPage("/index").failureUrl("/index?error").defaultSuccessUrl("/main")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }
}