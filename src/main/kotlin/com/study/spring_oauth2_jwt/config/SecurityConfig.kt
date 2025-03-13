package com.study.spring_oauth2_jwt.config

import com.study.spring_oauth2_jwt.filter.CustomSuccessHandler
import com.study.spring_oauth2_jwt.filter.JWTFilter
import com.study.spring_oauth2_jwt.filter.LoginFilter
import com.study.spring_oauth2_jwt.jwt.JWTUtil
import com.study.spring_oauth2_jwt.service.CustomOAuth2UserService
import com.study.spring_oauth2_jwt.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val customSuccessHandler: CustomSuccessHandler,
    private val jwtUtil: JWTUtil,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { auth -> auth.disable() }
            .formLogin { auth -> auth.disable() }
            .httpBasic { auth -> auth.disable() }
            .authorizeHttpRequests { auth -> auth
                    .requestMatchers("/login", "/", "/join").permitAll()
                    .anyRequest().authenticated()
            }
//            .cors {  }
            .oauth2Login {
                it.userInfoEndpoint { user -> user
                    .userService(customOAuth2UserService)
                }
                    .successHandler(customSuccessHandler)
            }
            .addFilterAt(
                LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterAfter(JWTFilter(jwtUtil), LoginFilter::class.java)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    //AuthenticationManager Bean 등록
    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

}