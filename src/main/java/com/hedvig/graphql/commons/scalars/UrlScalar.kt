package com.hedvig.graphql.commons.scalars

import graphql.language.StringValue
import graphql.schema.*
import org.springframework.stereotype.Component
import java.net.URL

@Component
class URLScalar : GraphQLScalarType("URL", "A string representation of `java.net.URL`", object : Coercing<URL, String> {

  @Throws(CoercingSerializeException::class)
  override fun serialize(dataFetcherResult: Any?): String? {
    if (dataFetcherResult == null) {
      return null
    }

    if (dataFetcherResult !is URL) {
      throw CoercingSerializeException(
          String.format("dataFetcherResult is of wrong type: Expected %s, got %s",
              URL::class.java.toString(), dataFetcherResult.javaClass.toString()))
    }

    return dataFetcherResult.toString()
  }

  @Throws(CoercingParseValueException::class)
  override fun parseValue(input: Any): URL {
    try {
      return URL(input as String)
    } catch (e: Exception) {
      throw CoercingParseValueException("Could not parse value", e)
    }
  }

  @Throws(CoercingParseLiteralException::class)
  override fun parseLiteral(input: Any): URL {
    try {
      return URL((input as StringValue).value)
    } catch (e: Exception) {
      throw CoercingParseLiteralException("Could not parse literal", e)
    }
  }
})