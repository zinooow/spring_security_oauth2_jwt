package com.study.spring_oauth2_jwt.dto

import com.study.spring_oauth2_jwt.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: UserEntity
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(user.role.toString()))
    }
    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.username
    fun getName(): String = user.name
    fun getEmail(): String = user.email

}