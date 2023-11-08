package com.poisonedyouth.kotlinpact.adapter.persistence

import com.poisonedyouth.kotlinpact.domain.User
import com.poisonedyouth.kotlinpact.port.UserRepository

class MockUserRepository : UserRepository {
    private val users = mutableListOf(
        User(1, "John Doe", "john.doe@example.com")
    )

    override fun getUserById(id: Long): User? {
        return users.find { it.id == id }
    }

    override fun createUser(name: String, email: String): User {
        val newUser = User((users.size + 1).toLong(), name, email)
        users.add(newUser)
        return newUser
    }
}