package com.hedvig.extensions

import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext

private const val HEDVIG_TOKEN: String = "hedvig.token"
private const val ACCEPT_LANGUAGE: String = "Accept-Language"

fun DataFetchingEnvironment.getToken() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.getHeader(HEDVIG_TOKEN)
        ?: throw NullPointerException("GraphQLContext was null when trying to get $HEDVIG_TOKEN!")

fun DataFetchingEnvironment.getAcceptLanguage() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.getHeader(ACCEPT_LANGUAGE)

