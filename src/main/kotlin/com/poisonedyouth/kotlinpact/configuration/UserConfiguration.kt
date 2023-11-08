package com.poisonedyouth.kotlinpact.configuration

import com.poisonedyouth.kotlinpact.adapter.persistence.MockUserRepository
import com.poisonedyouth.kotlinpact.domain.UserUseCase
import com.poisonedyouth.kotlinpact.port.UserRepository
import com.poisonedyouth.kotlinpact.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {

    @Bean
    fun userRepository(): UserRepository = MockUserRepository()

    @Bean
    fun userUseCase(userRepository: UserRepository): UserUseCase = UserService(userRepository)
}