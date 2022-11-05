package com.sangmeebee.weatherlist.domain.exceptions

open class IllegalCacheException : IllegalStateException()
class DeleteCacheWeatherException : IllegalCacheException()
class PostCacheWeatherException : IllegalCacheException()
class GetCacheWeatherException : IllegalCacheException()
