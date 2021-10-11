package spring.music.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.boot.autoconfigure.security.reactive.StaticResourceRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.music.services.impl.MusicUserService;

import javax.validation.Path;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MusicUserService musicUserService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(MusicUserService musicUserService, PasswordEncoder passwordEncoder) {
        this.musicUserService = musicUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                    .antMatchers("/","/users/login", "/users/register").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/users/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home");
                   // .failureForwardUrl("users/login-err");//todo
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(musicUserService)
                .passwordEncoder(passwordEncoder);
    }
}
