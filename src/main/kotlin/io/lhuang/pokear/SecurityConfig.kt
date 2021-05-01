package io.lhuang.pokear

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
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
                            it.antMatchers(
                                    "/", // TODO decide what we actually want to allow
                                    "/error",
                                    "/webjars/**"
                            ).permitAll()
                            .anyRequest().authenticated()
                }
                .exceptionHandling { e ->
                    e.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                }
                .oauth2Login()
    }
}