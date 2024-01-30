package com.blog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/posts/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                .anyRequest().authenticated();



    }


/*   (userDetailsService) is the METHOD present inside the (WebSecurityConfigurerAdapter)
    --> Advantages
    1.In this method we can create our own (username & password)
 */
/*    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        UserDetails SaiAdmin= User.withUsername("saikiran").password("12345").roles("ADMIN").build();
        UserDetails raviUser= User.withUsername("ravilamani").password("lamani").roles("USER").build();

        return new InMemoryUserDetailsManager(SaiAdmin,raviUser);
    }*/
}
