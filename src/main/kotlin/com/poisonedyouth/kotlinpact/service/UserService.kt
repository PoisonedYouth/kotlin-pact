package com.poisonedyouth.kotlinpact.service

import com.poisonedyouth.kotlinpact.domain.User
import com.poisonedyouth.kotlinpact.domain.UserUseCase
import com.poisonedyouth.kotlinpact.port.UserRepository

class UserService(
    private val userRepository: UserRepository
) : UserUseCase {
    override fun findUser(id: Long): User? {
        return userRepository.getUserById(id)
    }

    override fun createUser(name: String, email: String): User {
        return userRepository.createUser(name, email)
    }
}