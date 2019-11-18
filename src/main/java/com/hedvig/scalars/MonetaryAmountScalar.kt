package com.hedvig.scalars

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.language.FloatValue
import graphql.language.ObjectValue
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.math.BigDecimal
import javax.money.MonetaryAmount
import org.javamoney.moneta.Money
import org.springframework.stereotype.Component

@Component
class MonetaryAmountScalar(objectMapper: ObjectMapper) : GraphQLScalarType("MonetaryAmount", "An object representation of `javax.money.MonetaryAmount`", object : Coercing<MonetaryAmount, Map<String, Any>> {

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any): Map<String, Any> {
        try {
            return objectMapper.convertValue(
                dataFetcherResult, object : TypeReference<Map<String, Any>>() {
            })
        } catch (e: Exception) {
            throw CoercingSerializeException("Could not serialize value", e)
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): MonetaryAmount {
        try {
            val inputMap = input as Map<*, *>
            return when {
                inputMap["amount"] is Double -> Money.of(
                    BigDecimal.valueOf(inputMap["amount"] as Double), inputMap["currency"] as String)
                inputMap["amount"] is Int -> Money.of(
                    BigDecimal.valueOf((inputMap["amount"] as Int).toLong()), inputMap["currency"] as String)
                inputMap["amount"] is String -> Money.of(
                    BigDecimal(inputMap["amount"] as String), inputMap["currency"] as String)
                else -> throw IllegalArgumentException("Amount \"" + inputMap["amount"] + "\" was not a valid number")
            }
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): MonetaryAmount {
        try {
            val objectValue = input as ObjectValue
            var amount: BigDecimal? = null
            var currency: String? = null
            for (field in objectValue.objectFields) {
                if (field.name == "amount") {
                    amount = (field.value as FloatValue).value
                }
                if (field.name == "currency") {
                    currency = (field.value as StringValue).value
                }
            }
            return Money.of(amount, currency)
        } catch (e: Exception) {
            throw CoercingParseValueException("Could not parse value", e)
        }
    }
})
