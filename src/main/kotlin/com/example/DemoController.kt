package com.example

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller
class DemoController {
    @Post("/testEndpoint")
    fun testEndpoint(
        @Body demoRequest: DemoRequest,
    ): HttpResponse<String> {
        demoRequest.listWithNullContent?.forEach {
            println(it)
            println(it.hashCode())
        }
        return HttpResponse.ok("OK")
    }

}

@Introspected // removing @Introspected or using ksp instead of kapt makes test green.
data class DemoRequest(
    val listWithNullContent: List<String>?,
)
