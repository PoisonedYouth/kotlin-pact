package com.poisonedyouth.kotlinpact.domain

data class User(val id: Long, val name: String, val email: String)

data class NewUser(val name: String, val email: String)