package com.study.spring_oauth2_jwt.service

import com.study.spring_oauth2_jwt.dto.CustomOAuth2User
import com.study.spring_oauth2_jwt.dto.GoogleResponse
import com.study.spring_oauth2_jwt.dto.NaverResponse
import com.study.spring_oauth2_jwt.dto.OAuth2Response
import com.study.spring_oauth2_jwt.entity.UserEntity
import com.study.spring_oauth2_jwt.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository
): DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        val oAuth2User = super.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        println(registrationId)
        println(oAuth2User.attributes.toString())

        lateinit var oAuth2Response: OAuth2Response
        when (registrationId) {
            "naver" -> {oAuth2Response = NaverResponse(oAuth2User.attributes)}
            "google" -> {oAuth2Response = GoogleResponse(oAuth2User.attributes)}
            else -> {
                return null
            }
        }

        val username = "${oAuth2Response.getProvider()}-${oAuth2Response.getProviderId()}"
        var userData: UserEntity? = userRepository.findByUsername(username)

        if (userData == null) {
            userData = UserEntity(
                username = username,
                email = oAuth2Response.getEmail(),
                name = oAuth2Response.getName(),
                password = "",
                role = "USER",
            )
        }else {
            userData.email = oAuth2Response.getEmail()
            userData.name = oAuth2Response.getName()
        }
        userRepository.save(userData)

        return CustomOAuth2User(userData)
    }
}