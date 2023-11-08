package com.poisonedyouth.kotlinpact.adapter.rest

import com.poisonedyouth.kotlinpact.domain.User

data class UserDto(val id: Long, val name: String, val email: String)

fun User.toUserDto() = UserDto(
    id = this.id,
    name = this.name,
    email = this.email
)