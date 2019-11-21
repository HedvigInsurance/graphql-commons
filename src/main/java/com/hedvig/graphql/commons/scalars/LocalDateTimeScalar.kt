package com.hedvig.graphql.commons.scalars

import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

@Component
class LocalDateTimeScalar : GraphQLScalarType("LocalDateTime", "An string representation of LocalDateTime", object : Coercing<LocalDateTime, String> {
    @Throws(CoercingSerializeException::class)
    override fun serialize(data: Any): String {
        return fmt.format(data as LocalDateTime)
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): LocalDateTime {
        return LocalDateTime.from(fmt.parse(input as String))
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): LocalDateTime {
        return LocalDateTime.from(fmt.parse(input as String))
    }
}) {
    companion object {
        val fmt: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }
}
