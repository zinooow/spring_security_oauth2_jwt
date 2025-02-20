package com.study.spring_oauth2_jwt.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var username: String,
    var password: String,
    var name: String = "",
    var email: String = "",
    var role: String,

) {

}