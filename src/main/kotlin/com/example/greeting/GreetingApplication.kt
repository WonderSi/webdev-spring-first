package com.example.greeting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.example.greeting.controller")

@SpringBootApplication
class GreetingApplication

fun main(args: Array<String>) {
    runApplication<GreetingApplication>(*args)
}