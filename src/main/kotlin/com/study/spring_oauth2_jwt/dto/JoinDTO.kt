package com.study.spring_oauth2_jwt.dto

data class JoinDTO(
    val username: String,
    val password: String,
    val name: String,
    val email: String
)