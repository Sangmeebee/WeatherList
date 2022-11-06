package com.sangmeebee.weatherlist.cache.exceptions

open class IllegalCacheException : IllegalStateException()
class DeleteCacheWeatherException : IllegalCacheException()
class PostCacheWeatherException : IllegalCacheException()
class GetCacheWeatherException : IllegalCacheException()
