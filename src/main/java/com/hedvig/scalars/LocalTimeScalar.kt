package com.hedvig.scalars

import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

@Component
class LocalTimeScalar : GraphQLScalarType("LocalTime", "A string representation of LocalTime", object : Coercing<LocalTime, String> {
    @Throws(CoercingSerializeException::class)
    override fun serialize(o: Any): String {
        return if (o is LocalTime) {
            fmt.format(o)
        } else {
            throw CoercingSerializeException("Could not serialize :" + o.toString() + "as a LocalTime")
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(o: Any): LocalTime {
        try {
            return LocalTime.from(fmt.parse(o as String))
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value as a Local Time", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(o: Any): LocalTime {

        try {

            return LocalTime.from(fmt.parse(o as String))
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Could parse literal as Local Time", e)
        }
    }
}) {
    companion object {
        private val fmt = DateTimeFormatter.ISO_LOCAL_TIME
    }
}
