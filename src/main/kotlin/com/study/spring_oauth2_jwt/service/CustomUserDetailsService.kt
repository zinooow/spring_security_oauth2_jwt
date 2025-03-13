package com.study.spring_oauth2_jwt.service

import com.study.spring_oauth2_jwt.dto.CustomUserDetails
import com.study.spring_oauth2_jwt.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)?: throw UsernameNotFoundException("User not found: $username")

        return CustomUserDetails(user)
    }
}