package com.hedvig.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.ZonedDateTime
import org.springframework.stereotype.Component

@Component
class ZonedDateTimeScalar : GraphQLScalarType("ZonedDateTime", "A string-representation of `java.time.ZonedDateTime`", object : Coercing<ZonedDateTime, String> {

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any?): String? {
        if (dataFetcherResult == null) {
            return null
        }

        if (dataFetcherResult !is ZonedDateTime) {
            throw CoercingSerializeException(
                String.format("dataFetcherResult is of wrong type: Expected %s, got %s",
                    ZonedDateTime::class.java.toString(), dataFetcherResult.javaClass.toString()))
        }

        return dataFetcherResult.toString()
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): ZonedDateTime {
        try {
            return ZonedDateTime.parse(input as String)
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): ZonedDateTime {
        try {
            return ZonedDateTime.parse((input as StringValue).value)
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Could not parse literal", e)
        }
    }
})
