package com.hedvig.graphql.commons.extensions

import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext

private const val HEDVIG_TOKEN: String = "hedvig.token"
private const val ACCEPT_LANGUAGE: String = "Accept-Language"
private const val X_FORWARDED_FOR: String = "x-forwarded-for"

fun DataFetchingEnvironment.getToken() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.getHeader(HEDVIG_TOKEN)
        ?: throw NullPointerException("$HEDVIG_TOKEN is missing!")

fun DataFetchingEnvironment.getTokenOrNull() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.getHeader(HEDVIG_TOKEN)

fun DataFetchingEnvironment.getAcceptLanguage() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.getHeader(ACCEPT_LANGUAGE)

fun DataFetchingEnvironment.getEndUserIp(): String? {
    val ip = this.getContext<GraphQLServletContext?>()?.httpServletRequest?.getHeader(X_FORWARDED_FOR)
    return if (ip?.contains(",") == true) {
        ip.split(",").first()
    } else {
        ip
    }
}
