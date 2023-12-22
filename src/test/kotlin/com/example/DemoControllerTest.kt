package com.example

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.netty.DefaultHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest
class DemoControllerTest {
    @Inject
    lateinit var httpClient: DefaultHttpClient

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @Test
    fun `should ignore null values in list`() {
        val url = embeddedServer.url.toString() + "/testEndpoint"
        httpClient.toBlocking().exchange(
            HttpRequest.POST(
                url, """
                {
                        "listWithNullContent": [null]
                }  
                """.trimIndent()
            ),
            String::class.java
        )
    }
}
