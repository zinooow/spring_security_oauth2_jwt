package com.study.spring_oauth2_jwt.dto

class GoogleResponse(
    private val attribute: Map<String, Any>,
):OAuth2Response {
    override fun getProvider(): String = "google"
    override fun getProviderId(): String = attribute["sub"] as String
    override fun getName(): String = attribute["name"] as String
    override fun getEmail(): String = attribute["email"] as String
}