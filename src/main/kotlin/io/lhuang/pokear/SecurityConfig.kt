package io.lhuang.pokear

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

/**
 * https://spring.io/guides/tutorials/spring-boot-oauth2/
 */
@EnableWebSecurity
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests {
                    it
                            .antMatchers(
                                    "/login.html",
                                    "/error",
                                    "/webjars/**"
                            ).permitAll()
                            .anyRequest().authenticated()
                }
                .csrf().disable()
                // Allow us to verify OpenID Connect tokens from Google
                .oauth2ResourceServer { oauth2ResourceServer ->
                    oauth2ResourceServer.jwt {
                        // TODO how to make this not hardcoded for just google?
                        jwt -> jwt.decoder(JwtDecoders.fromOidcIssuerLocation("https://accounts.google.com"))
                    }
                }
                .exceptionHandling { e ->
                    // TODO return 403 for rest apis and redirect for html files
                    // e.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    e.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login.html"))
                }
                // Allow us to perform the OAuth2 client flow (magic)
                .oauth2Login()
    }
}