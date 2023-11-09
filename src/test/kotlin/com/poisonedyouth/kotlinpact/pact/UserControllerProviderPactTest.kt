package com.poisonedyouth.kotlinpact.pact

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import au.com.dius.pact.provider.junitsupport.Consumer
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactFolder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@Provider("UserController")
@PactFolder("build/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Consumer("UserControllerClient")
class UserControllerProviderPactTest {

    @LocalServerPort
    var port: Int = 0

    @BeforeEach
    fun setupTestTarget(context: PactVerificationContext) {
        // Set up the HTTP test target; this could also point to a staging/test environment
        context.target = HttpTestTarget("localhost", port)
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext) {
        // This is where the actual provider verification takes place
        context.verifyInteraction()
    }

    @State("UserController is available") // This should match the given state in the consumer pact
    fun toUserExistsState() {
        // Set up data or mock responses needed for the provider to meet the contract
        // This could involve preparing a user with ID 1 in the database, etc.
    }
}