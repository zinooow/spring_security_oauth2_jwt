package com.study.spring_oauth2_jwt.dto

class NaverResponse(
    attributes: Map<String, Any>,
):OAuth2Response {

    private val attribute = attributes["response"] as Map<*, *>

    override fun getProvider(): String = "naver"
    override fun getProviderId(): String = attribute["id"] as String
    override fun getName(): String = attribute["name"] as String
    override fun getEmail(): String = attribute["email"] as String
}