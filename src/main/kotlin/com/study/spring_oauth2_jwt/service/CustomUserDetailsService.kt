package com.study.spring_oauth2_jwt.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class CustomUserDetailsService: UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        TODO("Not yet implemented")
    }
}