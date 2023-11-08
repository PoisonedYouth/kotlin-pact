package com.poisonedyouth.kotlinpact.port

import com.poisonedyouth.kotlinpact.domain.User

interface UserRepository {
    fun getUserById(id: Long): User?
    fun createUser(name: String, email: String): User
}