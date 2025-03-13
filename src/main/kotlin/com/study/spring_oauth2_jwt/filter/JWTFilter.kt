package com.study.spring_oauth2_jwt.filter

import com.study.spring_oauth2_jwt.dto.CustomUserDetails
import com.study.spring_oauth2_jwt.entity.UserEntity
import com.study.spring_oauth2_jwt.jwt.JWTUtil
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JWTFilter(
    private val jwtUtil: JWTUtil,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token:String = request.getHeader("access").orEmpty()

        if(token.isEmpty()) {
            println("token is null")
            filterChain.doFilter(request, response)
            return
        }
        if(jwtUtil.getCategoryFromToken(token)!="access") {
            println("token is invalid")
            return
        }
        try{
            jwtUtil.isExpired(token)
        }catch(e: ExpiredJwtException){
            println("token is expired")
        }

        val userDetails = CustomUserDetails(UserEntity(
            username = jwtUtil.getUsernameFromToken(token),
            password = "",
            role = jwtUtil.getRoleFromToken(token),
            email = "",
            name = ""
        ))

        val authToken = UsernamePasswordAuthenticationToken(userDetails, "" , userDetails.authorities)

        SecurityContextHolder.getContext().authentication = authToken

        filterChain.doFilter(request, response)
    }

}