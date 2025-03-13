package com.study.spring_oauth2_jwt.filter

import com.study.spring_oauth2_jwt.dto.CustomUserDetails
import com.study.spring_oauth2_jwt.jwt.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class LoginFilter(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val username: String = obtainUsername(request).toString()
        val password: String = obtainPassword(request).toString()

        val authenticationToken = UsernamePasswordAuthenticationToken(username, password, null)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val userDetails: CustomUserDetails = authResult.principal as CustomUserDetails
        val username: String = userDetails.username

        val authority: MutableCollection<out GrantedAuthority> = userDetails.authorities
        val itr: Iterator<GrantedAuthority> = authority.iterator()
        val auth: GrantedAuthority = itr.next()
        val role: String = auth.authority

        val access: String = jwtUtil.generateToken(username, role, "access")
        val refresh: String = jwtUtil.generateToken(username, role, "access")

        response.setHeader("access", access)

        val cookie = Cookie("refresh", refresh)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = jwtUtil.refreshExpiration.toInt()

        response.addCookie(cookie)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        response.status = 401
    }
}