package com.poisonedyouth.kotlinpact.domain

interface UserUseCase {
    fun findUser(id: Long): User?
    fun createUser(name: String, email: String): User
}