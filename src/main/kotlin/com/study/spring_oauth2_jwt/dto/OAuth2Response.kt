package com.study.spring_oauth2_jwt.dto

interface OAuth2Response {
    fun getProvider(): String
    fun getProviderId(): String
    fun getName(): String
    fun getEmail(): String
}