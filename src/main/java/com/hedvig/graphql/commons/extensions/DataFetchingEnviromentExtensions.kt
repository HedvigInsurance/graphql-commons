package com.hedvig.graphql.commons.extensions

import graphql.schema.DataFetchingEnvironment
import graphql.servlet.GraphQLContext

private const val HEDVIG_TOKEN: String = "hedvig.token"
private const val ACCEPT_LANGUAGE: String = "Accept-Language"

fun DataFetchingEnvironment.getToken(): String =
    this.getContext<GraphQLContext>()!!.httpServletRequest
        .map { r -> r.getHeader(HEDVIG_TOKEN) }
        .orElseThrow { throw NullPointerException("$HEDVIG_TOKEN is missing") }

fun DataFetchingEnvironment.getTokenOrNull(): String? =
    this.getContext<GraphQLContext>()?.httpServletRequest?.map { r -> r.getHeader(HEDVIG_TOKEN) }?.orElse(null)

fun DataFetchingEnvironment.getAcceptLanguage(): String? =
    this.getContext<GraphQLContext>()?.httpServletRequest?.map { r -> r.getHeader(ACCEPT_LANGUAGE) }?.orElse(null)
