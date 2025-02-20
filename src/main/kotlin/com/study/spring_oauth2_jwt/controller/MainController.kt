package com.study.spring_oauth2_jwt.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {

    @GetMapping("main")
    @ResponseBody
    fun main() = "main"
}