package io.lhuang.pokear

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.web.bind.annotation.RestController

/**
 * https://spring.io/guides/tutorials/spring-boot-oauth2/
 */
@SpringBootApplication
@RestController
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests {
                    it
                            .antMatchers(
                                    "/", // TODO decide what we actually want to allow
                                    "/error",
                                    "/webjars/**"
                            ).permitAll()
                            .anyRequest().authenticated()
                }
                .csrf().disable()
                .exceptionHandling { e ->
                    // TODO return 403 for rest apis and redirect for html files
                    // e.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    e.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/oauth2/authorization/github"))
                }
                .oauth2Login()
    }
}