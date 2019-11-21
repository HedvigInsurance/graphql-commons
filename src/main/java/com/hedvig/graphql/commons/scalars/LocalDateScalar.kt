package com.hedvig.graphql.commons.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.LocalDate
import org.springframework.stereotype.Component

@Component
class LocalDateScalar : GraphQLScalarType("LocalDate", "A string representation of `java.time.LocalDate`", object : Coercing<LocalDate, String> {

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any?): String? {
        if (dataFetcherResult == null) {
            return null
        }

        if (dataFetcherResult !is LocalDate) {
            throw CoercingSerializeException(
                String.format("dataFetcherResult is of wrong type: Expected %s, got %s",
                    LocalDate::class.java.toString(), dataFetcherResult.javaClass.toString()))
        }

        return dataFetcherResult.toString()
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): LocalDate {
        try {
            return LocalDate.parse(input as String)
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): LocalDate {
        try {
            return LocalDate.parse((input as StringValue).value)
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Could not parse literal", e)
        }
    }
})
