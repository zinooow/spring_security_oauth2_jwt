package com.study.spring_oauth2_jwt.filter

import com.study.spring_oauth2_jwt.dto.CustomOAuth2User
import com.study.spring_oauth2_jwt.jwt.JWTUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.security.core.Authentication

@Component
class CustomSuccessHandler(
    private val jwtUtil: JWTUtil
): SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val user: CustomOAuth2User = authentication.principal as CustomOAuth2User
        val username = user.getUsername()
        val role = authentication.authorities.first().toString()

        val access = jwtUtil.generateToken(username, role, "access")
        val refresh = jwtUtil.generateToken(username, role, "refresh")

        response.setHeader("access", access)

        val cookie = Cookie("access_token", access)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = 3600

        response.addCookie(cookie)
        response.sendRedirect("http://localhost:3000/")

    }
}