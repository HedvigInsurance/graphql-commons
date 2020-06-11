package com.hedvig.graphql.commons.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component

@Component
class URLScalar : GraphQLScalarType("URL", "A string representation of a url", object : Coercing<String, String> {

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any?): String? {
        if (dataFetcherResult == null) {
            return null
        }

        if (dataFetcherResult !is String) {
            throw CoercingSerializeException(
                String.format("dataFetcherResult is of wrong type: Expected %s, got %s",
                    String::class.java.toString(), dataFetcherResult.javaClass.toString()))
        }
        return dataFetcherResult
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): String {
        try {
            return input as String
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): String {
        try {
            return (input as StringValue).value
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Could not parse literal", e)
        }
    }
})
