package com.study.spring_oauth2_jwt.jwt

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JWTUtil(
    @Value("\${spring.jwt.secret}")
    private val secret:String,
    @Value("\${spring.jwt.access-expiration}")
    val accessExpiration: Long,
    @Value("\${spring.jwt.refresh-expiration}")
    val refreshExpiration: Long,
) {


    private val key:SecretKey = SecretKeySpec(secret.toByteArray(), Jwts.SIG.HS256.key().build().algorithm)
//    private val key:SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(username: String, role: String, category: String): String{

        val expiration: Long = if (category=="refresh") refreshExpiration else accessExpiration

        return Jwts.builder()
            .claim("username", username)
            .claim("role", role)
            .claim("category", category)
            .signWith(key)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis()+expiration))
            .compact()
    }

    fun isExpired(token: String): Boolean{
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.expiration.before(Date())
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload["username"] as String
    }

    fun getRoleFromToken(token: String): String {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload["role"] as String
    }

    fun getCategoryFromToken(token: String): String {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload["category"] as String
    }

}
