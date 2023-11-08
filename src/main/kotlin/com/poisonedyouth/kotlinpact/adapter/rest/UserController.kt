package com.poisonedyouth.kotlinpact.adapter.rest

import com.poisonedyouth.kotlinpact.domain.NewUser
import com.poisonedyouth.kotlinpact.domain.UserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userUseCase: UserUseCase) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDto> {
        val user = userUseCase.findUser(id)
        return if (user != null) {
            ResponseEntity(user.toUserDto(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createUser(@RequestBody user: NewUser): ResponseEntity<UserDto> {
        val newUser = userUseCase.createUser(user.name, user.email)
        return ResponseEntity(newUser.toUserDto(), HttpStatus.CREATED)
    }
}
