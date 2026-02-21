package com.example.greeting.controller

import com.example.greeting.GreetingApplication
import com.example.greeting.dto.GreetingMain
import com.example.greeting.dto.GreetingUser
import com.example.greeting.dto.User
import com.example.greeting.dto.UserData
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/greeting")
class GreetingController {

    private val users: MutableMap<String, User> = ConcurrentHashMap()

    // GET /greeting
    @GetMapping
    fun greeting(@RequestParam(required = false) id: String?): ResponseEntity<*> {
        return if (id != null) {
            val user = users[id]
            if (user != null) {
                ResponseEntity.ok(UserData(user.name, user.surname))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
            }
        } else {
            ResponseEntity.ok(GreetingMain())
        }
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<*> {
        val user = users[id]
        return if (user != null) {
            ResponseEntity.ok(UserData(user.name, user.surname))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        }
    }

    @PostMapping
    fun postUser(@RequestBody userData: UserData): ResponseEntity<GreetingUser> {
        val userId = UUID.randomUUID().toString()
        val user = User(
            id = userId,
            name = userData.name,
            surname = userData.surname
        )
        users[userId] = user

        val response = GreetingUser(
            text = "Hello, ${userData.surname} ${userData.name}",
            id = userId
        )

        return ResponseEntity.ok(response)
    }
}