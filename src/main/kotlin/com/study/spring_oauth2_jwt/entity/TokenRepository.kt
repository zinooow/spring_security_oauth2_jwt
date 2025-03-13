package com.study.spring_oauth2_jwt.entity

import jakarta.persistence.*

@Entity
@Table(name = "token")
class TokenRepository(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,
    val username:String,
    val refreshToken:String,
) {

}