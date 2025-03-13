package com.study.spring_oauth2_jwt.dto

import com.study.spring_oauth2_jwt.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val user: UserEntity
): OAuth2User {
    override fun getName(): String = user.name

    override fun getAttributes(): MutableMap<String, Any>? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.role.split("").map { role -> SimpleGrantedAuthority("ROLE_$role") }.toCollection(ArrayList())
    }

    fun getUsername(): String = user.username

    fun getEmail(): String = user.email
}