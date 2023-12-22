package com.example

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.jackson.JacksonConfiguration
import io.micronaut.jackson.ObjectMapperFactory
import jakarta.inject.Singleton

// The commented-out JacksonObjectMapperFactory further down works, but it doesn't subclass ObjectMapperFactory which
// causes other problems, e.g. when trying to push logger config with build-in loggers-endpoint.
@Factory
@Replaces(ObjectMapperFactory::class)
class JacksonObjectMapperFactory: ObjectMapperFactory() {

    override fun jsonFactory(jacksonConfiguration: JacksonConfiguration?): JsonFactory {
        return super.jsonFactory(jacksonConfiguration)
    }

    @Replaces(ObjectMapper::class)
    @Singleton
    override fun objectMapper(
        jacksonConfiguration: JacksonConfiguration?,
        jsonFactory: JsonFactory?,
    ): ObjectMapper {
        val mapper = super.objectMapper(jacksonConfiguration, jsonFactory)

        @Suppress("CommentWrapping")
        mapper.setDefaultSetterInfo(
            JsonSetter.Value.construct(
                /* nulls = */ Nulls.DEFAULT,
                /* contentNulls = */ Nulls.SKIP
            )
        )

        return mapper
    }
}

//@Factory
//@Replaces(ObjectMapperFactory::class)
//class JacksonObjectMapperFactory {
//
//    private val objectMapperFactory = ObjectMapperFactory()
//
//    @Replaces(ObjectMapper::class)
//    @Singleton
//    fun objectMapper(
//        jacksonConfiguration: JacksonConfiguration?,
//        jsonFactory: JsonFactory?,
//    ): ObjectMapper {
//        val mapper = objectMapperFactory.objectMapper(jacksonConfiguration, jsonFactory)
//
//        @Suppress("CommentWrapping")
//        mapper.setDefaultSetterInfo(
//            JsonSetter.Value.construct(
//                /* nulls = */ Nulls.DEFAULT,
//                /* contentNulls = */ Nulls.SKIP
//            )
//        )
//
//        return mapper
//    }
//}
