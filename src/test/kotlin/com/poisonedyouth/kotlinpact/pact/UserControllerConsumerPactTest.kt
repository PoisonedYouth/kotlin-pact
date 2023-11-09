package com.poisonedyouth.kotlinpact.pact

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import com.jayway.jsonpath.JsonPath
import com.poisonedyouth.kotlinpact.domain.NewUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

@ExtendWith(PactConsumerTestExt::class)
class UserControllerConsumerPactTest {

    @Pact(consumer = "UserControllerClient", provider = "UserController")
    fun getUserPact(builder: PactDslWithProvider): V4Pact {
        return builder
            .given("UserController is available")
            .uponReceiving("A request to retrieve user")
            .path("/user/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(
                """
                {
                    "id": 1,
                    "name": "John Doe",
                    "email": "john.doe@example.com"
                }
            """.trimIndent()
            )
            .toPact(V4Pact::class.java)
    }

    @Test
    @PactTestFor(pactMethod = "getUserPact")
    fun verifyUserControllerGetUserRequest(mockServer: MockServer) {
        val restTemplate = RestTemplate()
        val userUrl = "${mockServer.getUrl()}/user"
        val response = restTemplate.getForEntity("$userUrl/1", String::class.java)

        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(JsonPath.read<Int>(response.body, "\$.id")).isEqualTo(1)
        assertThat(JsonPath.read<String>(response.body, "\$.name")).isEqualTo("John Doe")
        assertThat(JsonPath.read<String>(response.body, "\$.email")).isEqualTo("john.doe@example.com")
    }

    @Pact(consumer = "UserControllerClient", provider = "UserController")
    fun createtUserPact(builder: PactDslWithProvider): V4Pact {
        return builder
            .given("UserController is available")
            .uponReceiving("A request to create a user")
            .path("/user")
            .method("POST")
            .headers("Content-Type", "application/json")
            .body(
                """
                {
                    "name": "Max Cavalera",
                    "email": "max.cavalera@example.com"
                }
            """.trimIndent()
            )
            .willRespondWith()
            .status(201)
            .body(
                """
                {
                    "id": 2,
                    "name": "Max Cavalera",
                    "email": "max.cavalera@example.com"
                }
            """.trimIndent()
            )
            .toPact(V4Pact::class.java)
    }

    @Test
    @PactTestFor(pactMethod = "createtUserPact")
    fun verifyUserControllerCreateUserRequest(mockServer: MockServer) {
        val restTemplate = RestTemplate()
        val userUrl = "${mockServer.getUrl()}/user"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val response = restTemplate.postForEntity(
            userUrl,
            NewUser("Max Cavalera", "max.cavalera@example.com"),
            String::class.java,
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(JsonPath.read<Int>(response.body, "\$.id")).isEqualTo(2)
        assertThat(JsonPath.read<String>(response.body, "\$.name")).isEqualTo("Max Cavalera")
        assertThat(JsonPath.read<String>(response.body, "\$.email")).isEqualTo("max.cavalera@example.com")
    }
}