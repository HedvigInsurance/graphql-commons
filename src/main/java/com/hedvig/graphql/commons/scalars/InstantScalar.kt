package com.hedvig.graphql.commons.scalars

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.Instant
import org.springframework.stereotype.Component

@Component
class InstantScalar(objectMapper: ObjectMapper) : GraphQLScalarType("Instant", "An epoch representation of a `java.time.instant`", object : Coercing<Instant, String> {
    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any): String {
        try {
            return objectMapper.writeValueAsString(dataFetcherResult).replace("\"", "")
        } catch (e: Exception) {
            throw CoercingSerializeException("Unable to serialize value", e)
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): Instant {
        try {
            return Instant.parse(input as String)
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): Instant {
        try {
            return Instant.parse(input as String)
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Could not parse literal", e)
        }
    }
})
