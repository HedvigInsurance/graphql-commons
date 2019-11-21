package com.hedvig.graphql.commons.type

import java.math.BigDecimal
import java.math.RoundingMode
import javax.money.MonetaryAmount

data class MonetaryAmountV2(val amount: String, val currency: String) {
    companion object {
        fun of(monetaryAmount: MonetaryAmount): MonetaryAmountV2 {
            val amount = monetaryAmount.number.numberValueExact(BigDecimal::class.java)
            return of(amount, monetaryAmount.currency.toString())
        }

        fun of(amount: BigDecimal, currency: String): MonetaryAmountV2 {
            return MonetaryAmountV2(amount.setScale(2, RoundingMode.HALF_UP).toPlainString(), currency)
        }

        fun of(amount: Double, currency: String): MonetaryAmountV2 {
            return MonetaryAmountV2(
                BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).toPlainString(),
                currency
            )
        }
    }
}
