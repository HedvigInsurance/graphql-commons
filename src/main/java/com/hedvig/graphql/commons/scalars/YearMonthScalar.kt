package com.hedvig.graphql.commons.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.YearMonth
import org.springframework.stereotype.Component

@Component
class YearMonthScalar : GraphQLScalarType("YearMonth", "A string-representation of `java.time.YearMonth`", object : Coercing<YearMonth, String> {

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any?): String? {
        if (dataFetcherResult == null) {
            return null
        }

        if (dataFetcherResult !is YearMonth) {
            throw CoercingSerializeException(
                String.format(
                    "dataFetcherResult is of wrong type: Expected %s, got %s",
                    YearMonth::class.java.toString(), dataFetcherResult.javaClass.toString()))
        }

        return dataFetcherResult.toString()
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): YearMonth {
        try {
            return YearMonth.parse(input as String)
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): YearMonth {
        try {
            return YearMonth.parse((input as StringValue).value)
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Could not parse literal", e)
        }
    }
})
