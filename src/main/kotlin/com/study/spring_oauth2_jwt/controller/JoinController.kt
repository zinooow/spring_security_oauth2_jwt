package com.study.spring_oauth2_jwt.controller

import com.study.spring_oauth2_jwt.dto.JoinDTO
import com.study.spring_oauth2_jwt.service.JoinService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class JoinController(
    private val joinService: JoinService
) {

    @PostMapping("/join")
    fun join(@RequestBody joinDTO: JoinDTO): String {
        joinService.join(joinDTO)
        return "${joinDTO.username} join!"
    }
}