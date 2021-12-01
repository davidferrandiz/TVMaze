package com.davidferrandiz.presentation.exceptions.mapper

import com.davidferrandiz.presentation.exceptions.ExceptionType
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

private const val CODE_400 = 400
private const val CODE_500 = 500

fun handleExceptions(ex: Exception): ExceptionType {
    return when (ex) {
        is IOException -> ExceptionType.NetworkException()
        is UnknownHostException -> ExceptionType.NetworkException()
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> ExceptionType.GenericException()
    }
}

private fun apiErrorFromCodeException(code: Int): ExceptionType {
    return when (code) {
        in (CODE_400 until CODE_500) -> ExceptionType.ErrorException()
        else -> ExceptionType.NetworkException()
    }
}
