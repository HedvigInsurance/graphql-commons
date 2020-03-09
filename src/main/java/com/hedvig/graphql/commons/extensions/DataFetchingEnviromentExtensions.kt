package com.hedvig.graphql.commons.extensions

import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext
import javax.servlet.http.HttpServletRequest

private const val HEDVIG_TOKEN: String = "hedvig.token"
private const val ACCEPT_LANGUAGE: String = "Accept-Language"
private const val X_FORWARDED_FOR: String = "x-forwarded-for"
private const val USER_AGENT: String = "User-Agent"

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

fun DataFetchingEnvironment.isAndroid() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.isAndroid() ?: false
    ?: false

fun DataFetchingEnvironment.isIOS() =
    this.getContext<GraphQLServletContext?>()?.httpServletRequest?.isIOS() ?: false

fun HttpServletRequest.isIOS(): Boolean {
    return getHeader(USER_AGENT)?.contains(iOSAppUserAgentRegex) ?: false
}

fun HttpServletRequest.isAndroid(): Boolean {
    return getHeader(USER_AGENT)?.contains(androidAppUserAgentRegex) ?: false
}

private val iOSAppUserAgentRegex = Regex("^com\\.hedvig.+iOS")
private val androidAppUserAgentRegex = Regex("^com\\.hedvig.+Android")
