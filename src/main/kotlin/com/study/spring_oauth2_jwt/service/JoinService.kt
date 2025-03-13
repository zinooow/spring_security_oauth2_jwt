package com.study.spring_oauth2_jwt.service

import com.study.spring_oauth2_jwt.dto.JoinDTO
import com.study.spring_oauth2_jwt.entity.UserEntity
import com.study.spring_oauth2_jwt.repository.UserRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class JoinService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
) {

    fun join(joinDTO: JoinDTO): String {

        if (userRepository.existsByUsername(joinDTO.username)){
            throw DuplicateKeyException("User already exists")
        }

        userRepository.save(UserEntity(
            username = joinDTO.username,
            name = joinDTO.name,
            email = joinDTO.email,
            password = passwordEncoder.encode(joinDTO.password),
            role = "ROLE_USER"
        ))
        return "OK"
    }

}
