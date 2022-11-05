package com.sangmeebee.weatherlist.remote.exceptions

class IllegalLocationException : IllegalArgumentException()
class EmptyResultLocationException : IllegalArgumentException()
class IllegalAppTokenException : IllegalArgumentException()
class DisConnectNetworkException : IllegalStateException()

const val ILLEGAL_LOCATION = "400"
const val EMPTY_RESULT_LOCATION = "404"
const val ILLEGAL_APP_TOKEN = "401"
