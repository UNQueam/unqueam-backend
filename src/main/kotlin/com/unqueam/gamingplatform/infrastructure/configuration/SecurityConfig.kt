package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.application.auth.CustomUserDetailsService
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Suppress("DEPRECATION")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SecurityConfig ( @Autowired val jwtAuthenticationFilter : JwtAuthenticationFilter){

    @Bean
    fun authenticationManager(http: HttpSecurity, passwordEncoder: PasswordEncoder, userDetailService: CustomUserDetailsService): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf()
            .disable()
            .cors()
            .and()
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.PUT, "/api/games/{id}/hide", "/api/games/{id}/expose").hasRole("DEVELOPER")
                .requestMatchers(HttpMethod.POST, "/api/games").hasRole("DEVELOPER")
                .requestMatchers(HttpMethod.POST, "/api/games/{id}/comments").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/games/{id}/comments/{commentId}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/games/{id}/comments/{commentId}").authenticated()

            .requestMatchers("/api/games", "/api/games/**", "/api/genres", "/api/auth/signIn", "/api/auth/signUp")
            .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/requests").hasAnyRole("USER")
            .requestMatchers(HttpMethod.GET, "/api/requests/**").hasAnyRole("ADMIN")
            .requestMatchers("/api/auth/logout").authenticated()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
